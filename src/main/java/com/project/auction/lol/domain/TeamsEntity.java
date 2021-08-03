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
@Entity(name = "teams")
public class TeamsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long id;

    private Long leaderId;

    private String teamName;

    private Long points = 1500l;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<ParticipantsEntity> participants = new ArrayList<>();


    @Builder
    public TeamsEntity(Long leaderId, String teamName) {
        this.leaderId = leaderId;
        this.teamName = teamName;
    }

    private void minusPoints(Long point) {
        this.points -= point;
    }

    public void updateTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void addParticipants(ParticipantsEntity participantsEntity) {
        this.participants.add(participantsEntity);
        this.minusPoints(participantsEntity.getPoint());
        participantsEntity.updateTeam(this);
    }

}
