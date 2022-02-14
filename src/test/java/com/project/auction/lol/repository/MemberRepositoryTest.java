package com.project.auction.lol.repository;

import com.project.auction.lol.domain.MemberEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("참가자 등록 및 이름, 포지션 조회")
    public void saveParticipantsTest() {

        // given
        final MemberEntity entity = MemberEntity.builder()
                .summonerName("감귤or가씨")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .comment("화이팅")
                .build();
        final MemberEntity memberEntity = memberRepository.save(entity);

        // when
        final Optional<MemberEntity> nameResult = memberRepository.findBySummonerName("감귤or가씨");

        // then
        assertTrue(nameResult.isPresent());
        assertThat(nameResult.get().getSummonerName()).isEqualTo("감귤or가씨");

    }

    @Test
    @DisplayName("포지션으로 멤버 찾기")
    public void findByMainPosition(){

        final MemberEntity entity = MemberEntity.builder()
                .summonerName("감귤or가씨1")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .comment("화이팅")
                .build();
        final MemberEntity memberEntity = memberRepository.save(entity);

        memberRepository.save(memberEntity);

        final List<MemberEntity> positionResults = memberRepository.findByMainPosition("SUP");
        assertThat(positionResults.size()).isEqualTo(1);
    }

    @Test
    public void findAll(){
        // given
        final MemberEntity entity = MemberEntity.builder()
                .summonerName("감귤or가씨")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .comment("화이팅")
                .build();
        memberRepository.save(entity);

        final MemberEntity entity1 = MemberEntity.builder()
                .summonerName("감귤or가씨1")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .comment("화이팅")
                .build();
        memberRepository.save(entity1);

        // when
        List<MemberEntity> participantsEntities = memberRepository.findAll();

        // then
        assertThat(participantsEntities.size()).isEqualTo(2);

    }

}