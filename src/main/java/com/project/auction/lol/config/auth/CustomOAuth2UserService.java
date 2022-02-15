package com.project.auction.lol.config.auth;

import com.project.auction.lol.config.auth.dto.OAuthAttributes;
import com.project.auction.lol.config.auth.dto.SessionUser;
import com.project.auction.lol.domain.user.User;
import com.project.auction.lol.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /**
         * registrationId : 현재 로그인 진행중인 서비스를 구분하는 코드 (네이버/구글)
         * userNameAttributeName : OAuth2 로그인 진행 시 키가 되는 필드값 (PK 의미) - 구글 기본코드는 sub, 네이버는 지원하지 않음
         * OAuthAttributes : OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담은 클래스
         * SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto클래스
         */

        //	/**
        //	 * Constructs a {@code DefaultOAuth2User} using the provided parameters.
        //	 * @param authorities the authorities granted to the user
        //	 * @param attributes the attributes about the user
        //	 * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
        //	 * {@link #getAttributes()}
        //	 */
        //	public DefaultOAuth2User(Collection<? extends GrantedAuthority > authorities, Map<String, Object> attributes,
        //                String nameAttributeKey) {
        //            Assert.notEmpty(authorities, "authorities cannot be empty");
        //            Assert.notEmpty(attributes, "attributes cannot be empty");
        //            Assert.hasText(nameAttributeKey, "nameAttributeKey cannot be empty");
        //            if (!attributes.containsKey(nameAttributeKey)) {
        //                throw new IllegalArgumentException("Missing attribute '" + nameAttributeKey + "' in attributes");
        //            }
        //            this.authorities = Collections.unmodifiableSet(new LinkedHashSet<>(this.sortAuthorities(authorities)));
        //            this.attributes = Collections.unmodifiableMap(new LinkedHashMap<>(attributes));
        //            this.nameAttributeKey = nameAttributeKey;
        //        }
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture())) // 사용자 정보가 변경되었을 경우 이름이나 프로필사진 변경 용도
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
