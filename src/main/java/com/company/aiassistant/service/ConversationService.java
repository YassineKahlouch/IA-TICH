package com.company.aiassistant.service;

import com.company.aiassistant.entity.Conversation;
import com.company.aiassistant.entity.User;
import com.company.aiassistant.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public Conversation createConversation(User user) {

        Conversation conversation = Conversation.builder()
                .title("Nouvelle discussion")
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        return conversationRepository.save(conversation);
    }

    public List<Conversation> getUserConversations(User user) {
        return conversationRepository.findByUser(user);
    }

    public Conversation getConversationById(Long id) {
        return conversationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Conversation introuvable"));
    }

    public Conversation save(
            Conversation conversation
    ) {
        return conversationRepository.save(conversation);
    }

}
