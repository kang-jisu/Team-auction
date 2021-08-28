package com.project.auction.lol.repository;

import com.project.auction.lol.domain.ParticipantsEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ParticipantsRepositoryTest {

    @Autowired
    ParticipantsRepository participantsRepository;
    @AfterEach
    public void cleanup() {
    }

    @Test
    @DisplayName("참가자 등록 및 이름, 포지션 조회")
    public void saveParticipantsTest() {

        // given
        String summonerName = "감귤or가씨";
        String mainPosition = "SUP";
        String subPositions = "MID";
        String currentTier = "silver2";
        String higestTier = "silver2";
        String comment = "화이팅";

        final ParticipantsEntity participantsEntity = participantsRepository.save(ParticipantsEntity.builder()
                .summonerName(summonerName)
                .mainPosition(mainPosition)
                .subPositions(subPositions)
                .currentTier(currentTier)
                .highestTier(higestTier)
                .comment(comment)
                .build());


        final Optional<ParticipantsEntity> nameResult = participantsRepository.findBySummonerName(summonerName);
        assertTrue(nameResult.isPresent());
        assertThat(nameResult.get().getSummonerName()).isEqualTo(summonerName);


        // given
        String summonerName1 = "감귤or가씨1";

        final ParticipantsEntity participantsEntity1 = participantsRepository.save(ParticipantsEntity.builder()
                .summonerName(summonerName1)
                .mainPosition(mainPosition)
                .subPositions(subPositions)
                .currentTier(currentTier)
                .highestTier(higestTier)
                .comment(comment)
                .build());

        participantsRepository.save(participantsEntity);

        final List<ParticipantsEntity> positionResults = participantsRepository.findByMainPosition("SUP");
        assertThat(positionResults.size()).isEqualTo(2);
    }

}