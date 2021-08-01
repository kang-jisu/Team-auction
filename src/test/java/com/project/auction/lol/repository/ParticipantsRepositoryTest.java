package com.project.auction.lol.repository;

import com.project.auction.lol.domain.ParticipantsEntity;
import junit.framework.TestCase;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParticipantsRepositoryTest extends TestCase {

    @Autowired
    ParticipantsRepository participantsRepository;

    @After
    public void cleanup(){
        participantsRepository.deleteAll();
    }

    @Test
    public void 참가자_등록(){

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
        ParticipantsEntity participantsEntity= participantsEntityList.get(0);
        Assertions.assertThat(participantsEntity.getSummonerName()).isEqualTo(summonerName);
    }
}