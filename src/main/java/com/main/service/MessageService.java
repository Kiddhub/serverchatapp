package com.main.service;


import com.main.model.Message;
import com.main.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(int senderId, int conversationId, String message, String messageType) {
        Message newMessage = new Message();
        newMessage.setSender_id(senderId);
        newMessage.setConversation_id(conversationId);
        newMessage.setMessage(message);
        newMessage.setCreate_at(LocalDateTime.now());

        if ("CHAT".equals(messageType)) {
            newMessage.setMessage_type("CHAT");
        } else if ("AUDIO".equals(messageType)) {
            newMessage.setMessage_type("AUDIO");
            newMessage.setMessage("Sent an audio meeting");
        } else if ("VIDEO".equals(messageType)) {
            newMessage.setMessage_type("VIDEO");
            newMessage.setMessage("Sent a video meeting");
        }

        return messageRepository.save(newMessage);
    }

}
