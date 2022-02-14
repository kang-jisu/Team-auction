package com.project.auction.lol.repository;

import com.project.auction.lol.domain.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

    @Query("select t from teams t join fetch t.participants")
    List<TeamEntity> findAllJoinFetch();
}
