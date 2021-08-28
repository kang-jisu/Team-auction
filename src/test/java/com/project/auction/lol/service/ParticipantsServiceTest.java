package com.project.auction.lol.service;

import com.project.auction.lol.domain.ParticipantsEntity;
import com.project.auction.lol.dto.ParticipantsSaveRequestDto;
import com.project.auction.lol.dto.ParticipantsSaveResponseDto;
import com.project.auction.lol.errors.ErrorCode;
import com.project.auction.lol.errors.MayoException;
import com.project.auction.lol.repository.ParticipantsRepository;
import org.junit.jupiter.api.BeforeEach;
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
public class ParticipantsServiceTest {

    @InjectMocks
    private ParticipantsService participantsService;

    @Mock
    private ParticipantsRepository participantsRepository;

    @Test
    @DisplayName("참가자 등록 실패 - 이미 존재하는 닉네임")
    public void findBySummonerName(){

        // given
        final ParticipantsSaveRequestDto dto = ParticipantsSaveRequestDto.builder()
                .summonerName("감귤or가씨")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .build();
        given(participantsRepository.findBySummonerName("감귤or가씨")).willReturn(Optional.of(ParticipantsEntity.builder().build()));

        // when
        final MayoException result = assertThrows(MayoException.class, ()->participantsService.save(dto));

        // then
        assertThat(result.getCode()).isEqualTo(ErrorCode.DUPLICATE_SUMMONER_NAME);
    }


    @Test
    @DisplayName("참가자 등록 성공")
    public void save(){
        //given
        final ParticipantsSaveRequestDto dto = ParticipantsSaveRequestDto.builder()
                .summonerName("감귤or가씨")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .build();

        final ParticipantsEntity participantsEntity = ParticipantsEntity.builder()
                .id(1l)
                .summonerName("감귤or가씨")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .comment("")
                .build();

        given(participantsRepository.findBySummonerName("감귤or가씨")).willReturn(Optional.empty());
        given(participantsRepository.save(any(ParticipantsEntity.class))).willReturn(participantsEntity);

        //when
        ParticipantsSaveResponseDto result = participantsService.save(dto);

        assertThat(result.getId()).isEqualTo(1l);
        verify(participantsRepository,times(1)).findBySummonerName("감귤or가씨");
        verify(participantsRepository,times(1)).save(any(ParticipantsEntity.class));
    }

    @Test
    public void findAll(){
        final ParticipantsEntity participantsEntity = ParticipantsEntity.builder()
                .id(1l)
                .summonerName("첫번째")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .comment("")
                .build();
        given(participantsRepository.findAll()).willReturn(Arrays.asList(participantsEntity));

        // when
        List<ParticipantsSaveResponseDto> list = participantsService.findAll();

        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getSummonerName()).isEqualTo("첫번째");
    }
}