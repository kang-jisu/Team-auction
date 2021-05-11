package com.project.freelec.springboot.service.posts;


import com.project.freelec.springboot.domain.posts.Posts;
import com.project.freelec.springboot.domain.posts.PostsRepository;
import com.project.freelec.springboot.web.dto.PostsListResponseDto;
import com.project.freelec.springboot.web.dto.PostsResponseDto;
import com.project.freelec.springboot.web.dto.PostsSaveRequestDto;
import com.project.freelec.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor // final이 선언된 모든 필드를 인자값으로 하는 생성자를 대신 생성해줌. ( Autowired 권장하지않음 )
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id = "+id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id = "+id));
        return new PostsResponseDto(entity);
    }

    /*
     * 등록,수정,삭제 기능이 전혀 없는 서비스 메소드에서 readonly옵션을 주면
     * 트랜잭션 범위는 유지하되 조회만 하므로 조회 속도가 개선됨
     */
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {

        /*
         * 람다식
         * .map(posts -> new PostsListResponseDto(posts))와 같은 의미
         * 레파지토리 결과로 넘어온 Posts의 stream을 map을 통해 dto로 변환-> list로 변환하는 메소드
         */
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow( ()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        postsRepository.delete(posts);
    }
}
