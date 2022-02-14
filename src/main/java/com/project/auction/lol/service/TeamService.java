package com.project.auction.lol.service;

import com.project.auction.lol.domain.MemberEntity;
import com.project.auction.lol.domain.TeamEntity;
import com.project.auction.lol.errors.ErrorCode;
import com.project.auction.lol.errors.MayoException;
import com.project.auction.lol.repository.MemberRepository;
import com.project.auction.lol.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TeamService {

    private TeamRepository teamRepository;
    private MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<String> findAllPariticipantsNames() {
        return extractParticipantsName(teamRepository.findAll());
    }

    private List<String> extractParticipantsName(List<TeamEntity> teams) {
        log.info(">>>>>>>>[모든 팀을 추출한다]<<<<<<<<<");
        log.info("Team Size : {}", teams.size());

        return teams.stream()
                .map(t -> t.getParticipants().get(0).getSummonerName())
                .collect(Collectors.toList());

    }

    @Transactional
    public void setTeamLeaderByPosition(String position)  {
        // 이미 팀이 생성되었다면
        // TODO: queryDSL로 exist 기능 구현
        if(!teamRepository.findAll().isEmpty()) throw new MayoException(ErrorCode.EXIST_TEAM, "이미 팀이 생성되었습니다.");

        List<MemberEntity> participants = memberRepository.findByMainPosition(position);

        for(MemberEntity entity: participants){
            if(entity.getTeam()!=null) throw new MayoException(ErrorCode.EXIST_TEAM, "이미 팀이 생성되었습니다.");

            entity.updatePoint(0l);
            TeamEntity teamEntity = TeamEntity.builder()
                                        .teamName(entity.getSummonerName()+"팀")
                                        .leaderId(entity.getId())
                                        .build();
            teamEntity.addParticipants(entity);
            teamRepository.save(teamEntity);
        }
    }
}
