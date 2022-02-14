package com.project.auction.lol.repository;

import com.project.auction.lol.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    List<MemberEntity> findByMainPosition(String mainPosition);

    Optional<MemberEntity> findBySummonerName(String summonerName);
}
