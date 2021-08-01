package com.project.auction.lol.service;

import com.project.auction.lol.domain.ParticipantsEntity;
import com.project.auction.lol.dto.ParticipantsSaveRequestDto;
import com.project.auction.lol.repository.ParticipantsRepository;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParticipantsServiceTest {

    @Autowired
    private ParticipantsService participantsService;

    @Autowired
    private ParticipantsRepository participantsRepository;

    @After
    public void cleanup(){
        participantsRepository.deleteAll();
    }

    @Test
    public void DTO데이터_participants테이블에_저장(){

        // given
        ParticipantsSaveRequestDto dto = ParticipantsSaveRequestDto.builder()
                .summonerName("감귤or가씨")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .build();

        // when
        participantsService.save(dto);

        ParticipantsEntity participantsEntity = participantsRepository.findAll().get(0);
        Assertions.assertThat(participantsEntity.getSummonerName()).isEqualTo(dto.getSummonerName());
    }
}