package com.main.repository;

import com.main.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant,Integer> {
    List<Participant> findByUserId(int userId);
}
