package com.project.auction.lol.service;

import com.project.auction.lol.domain.ParticipantsEntity;
import com.project.auction.lol.dto.ParticipantsSaveRequestDto;
import com.project.auction.lol.repository.ParticipantsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ParticipantsServiceTest {

    @Autowired
    private ParticipantsService participantsService;

    @Autowired
    private ParticipantsRepository participantsRepository;

    @AfterEach
    public void cleanup() {
        participantsRepository.deleteAll();
    }

    @Test
    public void DTO데이터_participants테이블에_저장() {

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