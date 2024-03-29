package com.project.auction.lol.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 접근권한 최소화. builder를 통한 생성자 하나만 둠
@Entity(name = "members")
public class MemberEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String summonerName;

    @Column(nullable = false)
    private String mainPosition;

    private String subPositions;

    @Column(length = 15, nullable = false)
    private String currentTier;

    @Column(length = 15, nullable = false)
    private String highestTier;

    private String comment;

    private Long point = -100L;

    @ManyToOne(fetch = FetchType.LAZY) // default 값 EAGER, LAZY로 변경해 즉시 조회 필요시 fetch join사용
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @Builder // 생성 시점에 안전하게 객체 생성 가능. 이후에 값 변경은 메서드 이용
    public MemberEntity(Long id, String summonerName, String mainPosition, String subPositions, String currentTier, String highestTier, String comment, Long point) {
        this.id = id;
        this.summonerName = summonerName;
        this.mainPosition = mainPosition;
        this.subPositions = subPositions;
        this.currentTier = currentTier;
        this.highestTier = highestTier;
        this.comment = comment;
        this.point = point;
    }

    public void updateTeam(TeamEntity teamEntity) {
        this.team = teamEntity;
    }

    public void updatePoint(long point) {
        this.point = point;
    }
}
