package com.project.auction.lol.web;

import com.project.auction.lol.dto.ParticipantsSaveRequestDto;
import com.project.auction.lol.service.ParticipantsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class ParticipantsController {
    private ParticipantsService participantsService;

    @PostMapping("/participants")
    public Long saveParticipants(@RequestBody ParticipantsSaveRequestDto dto) {
        return participantsService.save(dto);
    }
}
