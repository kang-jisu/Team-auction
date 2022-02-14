package com.project.auction.lol.dto;

import com.project.auction.lol.domain.MemberEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSaveRequestDto {

    @NotBlank(message = "닉네임을 입력해주세요")
    private String summonerName;

    @NotNull(message = "주 포지션을 입력해주세요.")
    @Pattern(regexp= "TOP|JUG|MID|ADC|SUP", message = "올바른 형식의 주포지션을 입력해주세요(TOP,JUG,MID,ADC,SUP)")
    private String mainPosition;

    @Pattern(regexp = "(TOP|JUG|MID|ADC|SUP)?(,(TOP|JUG|MID|ADC|SUP))*",message = "올바른 형식의 부포지션을 입력해주세요(TOP,JUG,MID,ADC,SUP)")
    private String subPositions;

    @NotNull(message = "현재 티어값을 입력해주세요")
    private String currentTier;
    @NotNull(message = "최고 티어값을 입력해주세요")
    private String highestTier;

    private String comment;


    @Builder // 생성 시점에 안전하게 객체 생성 가능. 이후에 값 변경은 메서드 이용
    public MemberSaveRequestDto(String summonerName, String mainPosition, String subPositions, String currentTier, String highestTier, String comment) {
        this.summonerName = summonerName;
        this.mainPosition = mainPosition;
        this.subPositions = subPositions;
        this.currentTier = currentTier;
        this.highestTier = highestTier;
        this.comment = comment;
    }

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .summonerName(summonerName)
                .mainPosition(mainPosition)
                .subPositions(subPositions)
                .currentTier(currentTier)
                .highestTier(highestTier)
                .build();
    }
}
