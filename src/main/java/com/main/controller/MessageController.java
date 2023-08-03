package com.main.controller;

import com.main.model.Conversation;
import com.main.model.Message;
import com.main.model.User;
import com.main.repository.MessageRepository;
import com.main.repository.UserRepository;
import com.main.service.ConversationService;
import com.main.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/sendMessages")
    public ResponseEntity<Message> sendMessage(
            @RequestParam("senderName") String senderName,
            @RequestParam("recipientName") String recipientName,
            @RequestParam("message") String message,
            @RequestParam("messageType") String messageType
    ){
        User sender = userRepository.findByEmail(senderName);
        User recipient = userRepository.findByEmail(recipientName);
        Conversation conversation = conversationService.createConversation(sender.getId(), recipient.getId());
        if (conversation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        int conversationId = conversation.getId();
        Message message1 = messageService.sendMessage(sender.getId(), conversationId,message,messageType);
        if(message != null){
            return ResponseEntity.ok(message1);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping("/listMessage")
    public ResponseEntity<List<Message>> getMessagesBetweenUsers(
            @RequestParam("senderName") String senderName,
            @RequestParam("receiverName") String receiverName
    ){
        User sender = userRepository.findByEmail(senderName);
        User receiver = userRepository.findByEmail(receiverName);
        List<Message> messages = messageRepository.findMessageByUser(sender.getId(),receiver.getId());
        if(messages.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(messages);
        }
    }
}
