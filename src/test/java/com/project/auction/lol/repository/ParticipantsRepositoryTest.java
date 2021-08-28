package com.project.auction.lol.repository;

import com.project.auction.lol.domain.ParticipantsEntity;
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

    @Test
    @DisplayName("참가자 등록 및 이름, 포지션 조회")
    public void saveParticipantsTest() {

        // given
        final ParticipantsEntity entity = ParticipantsEntity.builder()
                .summonerName("감귤or가씨")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .comment("화이팅")
                .build();
        final ParticipantsEntity participantsEntity = participantsRepository.save(entity);

        // when
        final Optional<ParticipantsEntity> nameResult = participantsRepository.findBySummonerName("감귤or가씨");

        // then
        assertTrue(nameResult.isPresent());
        assertThat(nameResult.get().getSummonerName()).isEqualTo("감귤or가씨");

    }

    @Test
    @DisplayName("포지션으로 멤버 찾기")
    public void findByMainPosition(){

        final ParticipantsEntity entity = ParticipantsEntity.builder()
                .summonerName("감귤or가씨1")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .comment("화이팅")
                .build();
        final ParticipantsEntity participantsEntity = participantsRepository.save(entity);

        participantsRepository.save(participantsEntity);

        final List<ParticipantsEntity> positionResults = participantsRepository.findByMainPosition("SUP");
        assertThat(positionResults.size()).isEqualTo(1);
    }

    @Test
    public void findAll(){
        // given
        final ParticipantsEntity entity = ParticipantsEntity.builder()
                .summonerName("감귤or가씨")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .comment("화이팅")
                .build();
        participantsRepository.save(entity);

        final ParticipantsEntity entity1 = ParticipantsEntity.builder()
                .summonerName("감귤or가씨1")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .comment("화이팅")
                .build();
        participantsRepository.save(entity1);

        // when
        List<ParticipantsEntity> participantsEntities = participantsRepository.findAll();

        // then
        assertThat(participantsEntities.size()).isEqualTo(2);

    }

}