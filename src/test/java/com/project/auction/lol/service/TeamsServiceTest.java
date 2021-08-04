package com.project.auction.lol.service;

import com.project.auction.lol.domain.ParticipantsEntity;
import com.project.auction.lol.domain.TeamsEntity;
import com.project.auction.lol.repository.ParticipantsRepository;
import com.project.auction.lol.repository.TeamsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
class TeamsServiceTest {

    @Autowired
    private TeamsService teamsService;
    @Autowired
    private TeamsRepository teamsRepository;

    @Autowired
    private ParticipantsRepository participantsRepository;

    @AfterEach
    public void cleanAll() {

        teamsRepository.deleteAll();
    }

    @BeforeEach
    public void setup() {

        log.info("setup");
        List<TeamsEntity> teams = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            TeamsEntity teamsEntity = TeamsEntity.builder()
                    .teamName(i + "팀")
                    .build();
            teamsEntity.addParticipants(ParticipantsEntity.builder()
                    .summonerName("감귤or가씨")
                    .currentTier("silver2")
                    .highestTier("silver2")
                    .mainPosition("SUP")
                    .point(200l)
                    .build()
            );
            teams.add(teamsEntity);
            teamsRepository.save(teamsEntity);
        }

    }

    @Test
    public void 팀으로_참가자_조회() {

        log.info("참가자 조회 시작" );
        // given
        List<String> participantsNames = teamsService.findAllPariticipantsNames();

        // then
        assertThat(participantsNames.size()).isEqualTo(5);
        log.info("참가자 조회 끝");
    }

    @Test
    public void 팀만_조회(){
        log.info("팀만 조회 시작" );
        List<TeamsEntity> teams = teamsRepository.findAll();
        log.info("팀만 조회 끝");
    }

    @Test
    public void fetchjoin(){
        log.info("fetch join 조회 ");
        List<TeamsEntity> teams = teamsRepository.findAllJoinFetch();
        log.info("fetch join 조회 끝 ");
    }

}