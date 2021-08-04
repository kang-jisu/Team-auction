package com.project.auction.lol.repository;

import com.project.auction.lol.domain.ParticipantsEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ParticipantsRepositoryTest {

    @Autowired
    ParticipantsRepository participantsRepository;

    @AfterEach
    public void cleanup() {
    }

    @Test
    public void 참가자_등록() {

        // given
        String summonerName = "감귤or가씨";
        String mainPosition = "SUP";
        String subPositions = "MID";
        String currentTier = "silver2";
        String higestTier = "silver2";
        String comment = "화이팅";

        participantsRepository.save(ParticipantsEntity.builder()
                .summonerName(summonerName)
                .mainPosition(mainPosition)
                .subPositions(subPositions)
                .currentTier(currentTier)
                .highestTier(higestTier)
                .comment(comment)
                .build());

        // when
        List<ParticipantsEntity> participantsEntityList = participantsRepository.findAll();

        // then
        ParticipantsEntity participantsEntity = participantsEntityList.get(0);
        Assertions.assertThat(participantsEntity.getSummonerName()).isEqualTo(summonerName);
    }

    @Test
    public void 포지션별_조회(){
        // given
        String position = "MID";

        // when
        List<ParticipantsEntity> entities = participantsRepository.findParticipantsEntitiesByMainPosition(position);

        Assertions.assertThat(entities.size()).isEqualTo(4);
    }
}