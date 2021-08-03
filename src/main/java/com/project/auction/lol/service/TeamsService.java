package com.project.auction.lol.service;

import com.project.auction.lol.domain.TeamsEntity;
import com.project.auction.lol.repository.TeamsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TeamsService {

    private TeamsRepository teamsRepository;

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
}
