package com.project.auction.lol.web;

import com.project.auction.lol.errors.MayoException;
import com.project.auction.lol.service.TeamsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
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
        if(!Pattern.matches(reg, position)) throw new MayoException(HttpStatus.BAD_REQUEST,"올바르지 않은 형식의 포지션입니다.");

        teamsService.setTeamLeaderByPosition(position);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
