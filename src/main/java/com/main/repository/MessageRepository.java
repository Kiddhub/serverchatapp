package com.main.repository;

import com.main.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {

    @Query("SELECT m FROM Message m WHERE m.sender_id = :senderId OR m.sender_id = :receiverId")
    List<Message> findMessageByUser(@Param("senderId") int senderId, @Param("receiverId") int receiverId);

}
