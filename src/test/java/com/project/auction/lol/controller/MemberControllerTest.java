package com.project.auction.lol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.project.auction.lol.config.auth.SecurityConfig;
import com.project.auction.lol.controller.api.MemberController;
import com.project.auction.lol.dto.MemberSaveRequestDto;
import com.project.auction.lol.dto.MemberSaveResponseDto;
import com.project.auction.lol.errors.ErrorCode;
import com.project.auction.lol.errors.MayoException;
import com.project.auction.lol.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MemberController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
public class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private WebApplicationContext context;
//
//    @BeforeEach
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }


    @Test
    public void mvcnull() {
        assertThat(mvc).isNotNull();
    }

    @WithMockUser(roles = " ")
    @Test
    @DisplayName("이미 등록된 사용자일 때 테스트")
    public void DuplicateSummonerNameErrorTest() throws Exception {

        final MemberSaveRequestDto dto = MemberSaveRequestDto.builder()
                .summonerName("감귤or가씨")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .build();

        given(memberService.save(any(MemberSaveRequestDto.class))).willThrow(new MayoException(ErrorCode.DUPLICATE_SUMMONER_NAME));

        mvc.perform(post("/api/v1/participants")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .content(objectMapper.writeValueAsString(dto))
                .with(oauth2Login())
                .with(csrf())) // securityConfig를 필터에서 제외해버려서 이거 넣어줌
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @WithMockUser(roles = "USER")
    @Test
    @DisplayName("validation으로 유효성 검증 에러핸들링 테스트")
    public void parameterValidationTest() throws Exception {

        final MemberSaveRequestDto validErrorDto = MemberSaveRequestDto.builder()
                .summonerName("감귤or가씨")
                .mainPosition("")
                .subPositions("MIDdd")
                .currentTier("silver2")
                .highestTier("")
                .build();

        given(memberService.save(any(MemberSaveRequestDto.class))).willThrow(new MayoException(ErrorCode.DUPLICATE_SUMMONER_NAME));

        mvc.perform(post("/api/v1/participants")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(validErrorDto))
                .with(oauth2Login())
                .with(csrf())) // securityConfig를 필터에서 제외해버려서 이거 넣어줌
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @WithMockUser(roles = "USER")
    @Test
    public void successTest() throws Exception {
        final MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .summonerName("새로운참가자")
                .mainPosition("TOP")
                .currentTier("platinum2")
                .highestTier("platinum2")
                .build();

        final MemberSaveResponseDto responseDto = MemberSaveResponseDto.builder()
                .id(1l)
                .summonerName("새로운참가자")
                .mainPosition("TOP")
                .currentTier("platinum2")
                .highestTier("platinum2")
                .build();

        given(memberService.save(requestDto)).willReturn(responseDto);

        mvc.perform(post("/api/v1/participants")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto))
                .with(oauth2Login())
                .with(csrf())) // securityConfig를 필터에서 제외해버려서 이거 넣어줌
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @WithMockUser(roles = "USER")
    @Test
    public void findAll() throws Exception {
        final MemberSaveResponseDto responseDto = MemberSaveResponseDto.builder()
                .id(1l)
                .summonerName("등록된 참가자")
                .mainPosition("TOP")
                .currentTier("platinum2")
                .highestTier("platinum2")
                .build();

        given(memberService.findAll()).willReturn(Arrays.asList(responseDto));

        mvc.perform(get("/api/v1/participants"))
                .andDo(print())
                .andExpect(result -> result.getResponse().getContentAsString().contains("등록된"))
                .andExpect(status().isOk())
                .andDo(print());

    }
}
