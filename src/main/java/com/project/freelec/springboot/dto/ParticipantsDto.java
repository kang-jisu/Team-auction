package com.project.freelec.springboot.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ParticipantsDto {

    private String summonerName;

    private String mainPosition;
    private String subPositions;

    private String currentTier;
    private String highestTier;

    private String comment;
}
