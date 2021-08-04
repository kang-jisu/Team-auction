package com.project.auction.lol.repository;

import com.project.auction.lol.domain.ParticipantsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantsRepository extends JpaRepository<ParticipantsEntity, Long> {

    List<ParticipantsEntity> findParticipantsEntitiesByMainPosition(String mainPosition);
}
