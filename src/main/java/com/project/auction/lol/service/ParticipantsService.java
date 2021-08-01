package com.project.auction.lol.service;


import com.project.auction.lol.dto.ParticipantsSaveRequestDto;
import com.project.auction.lol.repository.ParticipantsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class ParticipantsService {
    private ParticipantsRepository participantsRepository;

    @Transactional
    public Long save(@RequestBody ParticipantsSaveRequestDto dto) {
        return participantsRepository.save(dto.toEntity()).getId();
    }
}
