package com.project.auction.lol.domain;

import com.project.auction.lol.repository.MemberRepository;
import com.project.auction.lol.repository.MemberRepositoryTest;
import com.project.auction.lol.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class jpatest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("팀 추가 ")
    public void addMemberToTeam(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MemberEntity entity = MemberEntity.builder()
                .summonerName("감귤or가씨")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .comment("화이팅")
                .point(100L)
                .build();
        MemberEntity entity1= MemberEntity.builder()
                .summonerName("리더")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .comment("화이팅")
                .point(100L)
                .build();
        memberRepository.save(entity);
        memberRepository.save(entity1);
        em.flush();
        log.info("=======member entity 생성 ======");

        TeamEntity teamEntity = TeamEntity.builder()
                .teamName("team1")
                .leaderId(entity1.getId())
                .build();
        teamRepository.save(teamEntity);
        em.flush();
        teamEntity.addParticipants(entity);
        teamRepository.save(teamEntity);
        em.flush();
        log.info("======team entity 생성 ======");

        em.flush();
        List<MemberEntity> all = em.createQuery("select m from members m join fetch m.team", MemberEntity.class).getResultList();
        log.info("======team entity 생성 ======");
        System.out.println(all.get(0).getTeam().getTeamName());
        em.getTransaction().commit();
        em.close();
    }
}
