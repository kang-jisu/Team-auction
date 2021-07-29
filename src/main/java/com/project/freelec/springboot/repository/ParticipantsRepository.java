package com.project.freelec.springboot.repository;

import com.project.freelec.springboot.domain.ParticipantsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantsRepository extends JpaRepository<ParticipantsEntity,Long> {

}
