package com.project.auction.lol.dto;

import com.project.auction.lol.domain.ParticipantsEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ParticipantsSaveRequestDto {

    private String summonerName;

    private String mainPosition;
    private String subPositions;

    private String currentTier;
    private String highestTier;

    private String comment;


    @Builder // 생성 시점에 안전하게 객체 생성 가능. 이후에 값 변경은 메서드 이용
    public ParticipantsSaveRequestDto(String summonerName, String mainPosition, String subPositions, String currentTier, String highestTier, String comment) {
        this.summonerName = summonerName;
        this.mainPosition = mainPosition;
        this.subPositions = subPositions;
        this.currentTier = currentTier;
        this.highestTier = highestTier;
        this.comment = comment;
    }

    public ParticipantsEntity toEntity(){
        return ParticipantsEntity.builder()
                .summonerName(summonerName)
                .mainPosition(mainPosition)
                .subPositions(subPositions)
                .currentTier(currentTier)
                .highestTier(highestTier)
                .build();
    }
}
