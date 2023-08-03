package com.main.service;

import com.main.model.Conversation;
import com.main.model.Participant;
import com.main.model.User;
import com.main.repository.ConversationRepository;
import com.main.repository.ParticipantRepository;
import com.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private UserRepository userRepository;

    public Conversation createConversation(int creatorId, int recipientId ){
        List<Participant> participants = participantRepository.findByUserId(creatorId);
        for (Participant participant : participants){
                int conversationId = participant.getConversation_id();
                Optional<Conversation> conversation = conversationRepository.findById(conversationId);
                if(conversation.isPresent()){
                    List<Participant> recipients = participantRepository.findByUserId(recipientId);
                    for(Participant recipient: recipients){
                        if(recipient.getConversation_id() == conversationId){
                            return conversation.get();
                        }
                    }
                }
        }
        Optional<User> recipient = userRepository.findById(recipientId);
        Conversation conversation = new Conversation();
        conversation.setTitle(recipient.get().getName());
        conversation.setCreator_id(creatorId);
        conversation.setCreated_at(LocalDateTime.now());
        Conversation savedConversation = conversationRepository.save(conversation);

        Participant participant1 = new Participant();
        participant1.setUserId(creatorId);
        participant1.setConversation_id(savedConversation.getId());
        participant1.setType("SINGLE");
        participantRepository.save(participant1);

        Participant participant2 = new Participant();
        participant2.setUserId(recipientId);
        participant2.setConversation_id(savedConversation.getId());
        participant2.setType("SINGLE");
        participantRepository.save(participant2);
        return savedConversation;
    }

    public boolean checkConversationExistence(int creatorId, int recipientId) {
        List<Participant> creatorParticipants = participantRepository.findByUserId(creatorId);
        for (Participant participant : creatorParticipants) {
            int conversationId = participant.getConversation_id();
            Optional<Conversation> conversation = conversationRepository.findById(conversationId);
            if (conversation.isPresent()) {
                List<Participant> recipients = participantRepository.findByUserId(recipientId);
                for (Participant recipient : recipients) {
                    if (recipient.getConversation_id() == conversationId) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
