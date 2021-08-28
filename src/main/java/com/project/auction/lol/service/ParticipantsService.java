package com.project.auction.lol.service;


import com.project.auction.lol.domain.ParticipantsEntity;
import com.project.auction.lol.dto.ParticipantsSaveRequestDto;
import com.project.auction.lol.dto.ParticipantsSaveResponseDto;
import com.project.auction.lol.errors.ErrorCode;
import com.project.auction.lol.errors.MayoException;
import com.project.auction.lol.repository.ParticipantsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantsService {
    private final ParticipantsRepository participantsRepository;

    @Transactional
    public ParticipantsSaveResponseDto save(@RequestBody ParticipantsSaveRequestDto dto) {

        if (participantsRepository.findBySummonerName(dto.getSummonerName()).isPresent())
            throw new MayoException(ErrorCode.DUPLICATE_SUMMONER_NAME);

        ParticipantsEntity saved = participantsRepository.save(dto.toEntity());

        return ParticipantsSaveResponseDto.builder()
                .id(saved.getId())
                .summonerName(saved.getSummonerName())
                .mainPosition(saved.getMainPosition())
                .subPositions(saved.getSubPositions())
                .currentTier(saved.getCurrentTier())
                .highestTier(saved.getHighestTier())
                .build();
    }

}
