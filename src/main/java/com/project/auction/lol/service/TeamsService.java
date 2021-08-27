package com.project.auction.lol.service;

import com.project.auction.lol.domain.ParticipantsEntity;
import com.project.auction.lol.domain.TeamsEntity;
import com.project.auction.lol.errors.ErrorCode;
import com.project.auction.lol.errors.MayoException;
import com.project.auction.lol.repository.ParticipantsRepository;
import com.project.auction.lol.repository.TeamsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TeamsService {

    private TeamsRepository teamsRepository;
    private ParticipantsRepository participantsRepository;

    @Transactional(readOnly = true)
    public List<String> findAllPariticipantsNames() {
        return extractParticipantsName(teamsRepository.findAll());
    }

    private List<String> extractParticipantsName(List<TeamsEntity> teams) {
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
        if(!teamsRepository.findAll().isEmpty()) throw new MayoException(ErrorCode.EXIST_TEAM, "이미 팀이 생성되었습니다.");

        List<ParticipantsEntity> participants = participantsRepository.findByMainPosition(position);

        for(ParticipantsEntity entity: participants){
            if(entity.getTeam()!=null) throw new MayoException(ErrorCode.EXIST_TEAM, "이미 팀이 생성되었습니다.");

            entity.updatePoint(0l);
            TeamsEntity teamsEntity = TeamsEntity.builder()
                                        .teamName(entity.getSummonerName()+"팀")
                                        .leaderId(entity.getId())
                                        .build();
            teamsEntity.addParticipants(entity);
            teamsRepository.save(teamsEntity);
        }
    }
}
