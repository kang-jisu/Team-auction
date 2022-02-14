package com.project.auction.lol.service;

import com.project.auction.lol.domain.MemberEntity;
import com.project.auction.lol.dto.MemberSaveRequestDto;
import com.project.auction.lol.dto.MemberSaveResponseDto;
import com.project.auction.lol.errors.ErrorCode;
import com.project.auction.lol.errors.MayoException;
import com.project.auction.lol.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("참가자 등록 실패 - 이미 존재하는 닉네임")
    public void findBySummonerName(){

        // given
        final MemberSaveRequestDto dto = MemberSaveRequestDto.builder()
                .summonerName("감귤or가씨")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .build();
        given(memberRepository.findBySummonerName("감귤or가씨")).willReturn(Optional.of(MemberEntity.builder().build()));

        // when
        final MayoException result = assertThrows(MayoException.class, ()-> memberService.save(dto));

        // then
        assertThat(result.getCode()).isEqualTo(ErrorCode.DUPLICATE_SUMMONER_NAME);
    }


    @Test
    @DisplayName("참가자 등록 성공")
    public void save(){
        //given
        final MemberSaveRequestDto dto = MemberSaveRequestDto.builder()
                .summonerName("감귤or가씨")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .build();

        final MemberEntity memberEntity = MemberEntity.builder()
                .id(1l)
                .summonerName("감귤or가씨")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .comment("")
                .build();

        given(memberRepository.findBySummonerName("감귤or가씨")).willReturn(Optional.empty());
        given(memberRepository.save(any(MemberEntity.class))).willReturn(memberEntity);

        //when
        MemberSaveResponseDto result = memberService.save(dto);

        assertThat(result.getId()).isEqualTo(1l);
        verify(memberRepository,times(1)).findBySummonerName("감귤or가씨");
        verify(memberRepository,times(1)).save(any(MemberEntity.class));
    }

    @Test
    public void findAll(){
        final MemberEntity memberEntity = MemberEntity.builder()
                .id(1l)
                .summonerName("첫번째")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .comment("")
                .build();
        given(memberRepository.findAll()).willReturn(Arrays.asList(memberEntity));

        // when
        List<MemberSaveResponseDto> list = memberService.findAll();

        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getSummonerName()).isEqualTo("첫번째");
    }
}