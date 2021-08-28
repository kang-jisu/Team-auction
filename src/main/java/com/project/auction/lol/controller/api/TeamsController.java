package com.project.auction.lol.controller.api;

import com.project.auction.lol.errors.ErrorCode;
import com.project.auction.lol.errors.MayoException;
import com.project.auction.lol.service.TeamsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
@AllArgsConstructor
@Slf4j
public class TeamsController {

    private TeamsService teamsService;

    @GetMapping("/teams/leader/{position}")
    public ResponseEntity<?> setTeamLeaderByPosition(@PathVariable String position){

        String reg = "TOP|JUG|MID|ADC|SUP";
        position = position.toUpperCase();
        // TODO @valid로 변경해서 이부분 삭제 ?
        if(!Pattern.matches(reg, position)) throw new MayoException(ErrorCode.POSITION_NOT_FOUND,"올바르지 않은 포지션 형식입니다.");

        teamsService.setTeamLeaderByPosition(position);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
