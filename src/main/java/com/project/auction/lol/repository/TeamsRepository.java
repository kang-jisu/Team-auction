package com.project.auction.lol.repository;

import com.project.auction.lol.domain.TeamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamsRepository extends JpaRepository<TeamsEntity, Long> {

    @Query("select t from teams t join t.participants")
    List<TeamsEntity> findAllJoinFetch();
}
