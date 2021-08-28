package com.project.auction.lol.repository;

import com.project.auction.lol.domain.ParticipantsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantsRepository extends JpaRepository<ParticipantsEntity, Long> {

    List<ParticipantsEntity> findByMainPosition(String mainPosition);

    Optional<ParticipantsEntity> findBySummonerName(String summonerName);
}
