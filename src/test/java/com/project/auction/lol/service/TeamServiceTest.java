package com.project.auction.lol.service;

import com.project.auction.lol.domain.MemberEntity;
import com.project.auction.lol.domain.TeamEntity;
import com.project.auction.lol.errors.MayoException;
import com.project.auction.lol.repository.MemberRepository;
import com.project.auction.lol.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class teamerviceTest {

    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    public void cleanAll() {
        teamRepository.deleteAll();
    }

    @BeforeEach
    public void setup() {

    }

    @Test
    public void 팀으로_참가자_조회() {

        // given
        List<TeamEntity> team = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            TeamEntity teamEntity = TeamEntity.builder()
                    .teamName(i + "팀")
                    .build();
            teamEntity.addParticipants(MemberEntity.builder()
                    .summonerName("감귤or가씨")
                    .currentTier("silver2")
                    .highestTier("silver2")
                    .mainPosition("SUP")
                    .point(200l)
                    .build()
            );
            team.add(teamEntity);
            teamRepository.save(teamEntity);
        }
        List<String> participantsNames = teamService.findAllPariticipantsNames();

        // then
        assertThat(participantsNames.size()).isEqualTo(5);
    }

    @Test
    public void 팀만_조회() {
        List<TeamEntity> team = teamRepository.findAll();
    }

    @Test
    public void 에러_팀_생성시_이미존재() throws Exception {

        // given
        TeamEntity teamsEntity = TeamEntity.builder()
                .teamName("팀")
                .build();
        teamsEntity.addParticipants(MemberEntity.builder()
                .summonerName("감귤or가씨")
                .currentTier("silver2")
                .highestTier("silver2")
                .mainPosition("SUP")
                .point(200l)
                .build()
        );
        teamRepository.save(teamsEntity);
        String position = "MID";

        // when
        Exception e = Assertions.assertThrows(MayoException.class, () -> teamService.setTeamLeaderByPosition(position));

        // then
        assertThat(e.getMessage()).contains("이미");

    }


}