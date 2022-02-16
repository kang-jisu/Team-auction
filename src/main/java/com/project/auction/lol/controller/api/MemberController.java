package com.project.auction.lol.controller.api;

import com.project.auction.lol.dto.MemberSaveRequestDto;
import com.project.auction.lol.dto.MemberSaveResponseDto;
import com.project.auction.lol.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/api/v1/participants")
    public ResponseEntity<List<MemberSaveResponseDto>> findAllParticipants(){
        log.info("참가자 전체 조회");
        return ResponseEntity.ok(memberService.findAll());
    }

    @PostMapping("/api/v1/participants")
    public ResponseEntity<MemberSaveResponseDto> saveParticipants(@RequestBody @Valid MemberSaveRequestDto dto){
        log.info("participants 생성 요청 : {}", dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED.CREATED)
                        .body(memberService.save(dto));
    }
}
