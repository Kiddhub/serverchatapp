package com.main.controller;

import com.main.model.Conversation;
import com.main.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @PostMapping("/conversations")
    public ResponseEntity<Conversation> createConversation(
            @RequestParam int creatorId,
            @RequestParam int recipientId
    ){

        Conversation conversation = conversationService.createConversation(creatorId,recipientId);
        if(conversation != null){
            return  new ResponseEntity<>(conversation, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
