package com.project.auction.lol.web;

import com.project.auction.lol.dto.ParticipantsSaveRequestDto;
import com.project.auction.lol.dto.ParticipantsSaveResponseDto;
import com.project.auction.lol.service.ParticipantsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ParticipantsController {
    private final ParticipantsService participantsService;

    @PostMapping("/participants")
    public ResponseEntity<ParticipantsSaveResponseDto> saveParticipants(@RequestBody @Valid ParticipantsSaveRequestDto dto){
        log.info("participants 생성 요청 : {}", dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED.CREATED)
                        .body(participantsService.save(dto));
    }
}
