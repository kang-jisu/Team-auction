package com.project.auction.lol.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParticipantsSaveResponseDto {

    private Long id;
    private String summonerName;

    private String mainPosition;
    private String subPositions;

    private String currentTier;
    private String highestTier;

    private String comment;


    @Builder // 생성 시점에 안전하게 객체 생성 가능. 이후에 값 변경은 메서드 이용
    public ParticipantsSaveResponseDto(Long id, String summonerName, String mainPosition, String subPositions, String currentTier, String highestTier, String comment) {
        this.id = id;
        this.summonerName = summonerName;
        this.mainPosition = mainPosition;
        this.subPositions = subPositions;
        this.currentTier = currentTier;
        this.highestTier = highestTier;
        this.comment = comment;
    }
}
