package com.project.auction.lol.web;

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
        if(!Pattern.matches(reg, position)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            teamsService.setTeamLeaderByPosition(position);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            String message = "이미 팀이 생성되었습니다.";
            log.error(message+": "+position);
            return new ResponseEntity<>(message,HttpStatus.CONFLICT);
        }
    }
}
