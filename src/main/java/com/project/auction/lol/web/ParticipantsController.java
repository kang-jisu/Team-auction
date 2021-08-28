package com.project.auction.lol.web;

import com.project.auction.lol.dto.ParticipantsSaveRequestDto;
import com.project.auction.lol.service.ParticipantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ParticipantsController {
    private final ParticipantsService participantsService;

    @PostMapping("/participants")
    public ResponseEntity<Long> saveParticipants(@RequestBody ParticipantsSaveRequestDto dto){
        return ResponseEntity.ok(participantsService.save(dto).getId());
    }
}
