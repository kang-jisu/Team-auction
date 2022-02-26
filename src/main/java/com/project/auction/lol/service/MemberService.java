package com.project.auction.lol.service;


import com.project.auction.lol.domain.MemberEntity;
import com.project.auction.lol.dto.MemberSaveRequestDto;
import com.project.auction.lol.dto.MemberSaveResponseDto;
import com.project.auction.lol.errors.ErrorCode;
import com.project.auction.lol.errors.MayoException;
import com.project.auction.lol.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberSaveResponseDto save(@RequestBody MemberSaveRequestDto dto) {

        if (memberRepository.findBySummonerName(dto.getSummonerName()).isPresent())
            throw new MayoException(ErrorCode.DUPLICATE_SUMMONER_NAME,"이미 등록된 사용자입니다.");

        MemberEntity saved = memberRepository.save(dto.toEntity());

        return MemberSaveResponseDto.builder()
                .id(saved.getId())
                .summonerName(saved.getSummonerName())
                .mainPosition(saved.getMainPosition())
                .subPositions(saved.getSubPositions())
                .currentTier(saved.getCurrentTier())
                .highestTier(saved.getHighestTier())
                .build();
    }

    @Transactional(readOnly = true)
    public List<MemberSaveResponseDto> findAll() {
        List<MemberEntity> entities = memberRepository.findAll();
        return entities.stream().map(entity-> MemberSaveResponseDto.builder()
                .id(entity.getId())
                .summonerName(entity.getSummonerName())
                .mainPosition(entity.getMainPosition())
                .subPositions(entity.getSubPositions())
                .currentTier(entity.getCurrentTier())
                .highestTier(entity.getHighestTier())
                .build()).collect(Collectors.toList());
    }
}
