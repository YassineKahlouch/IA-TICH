package com.company.aiassistant.service;

import com.company.aiassistant.entity.Conversation;
import com.company.aiassistant.entity.Message;
import com.company.aiassistant.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Message saveMessage(
            Conversation conversation,
            String role,
            String content
    ) {

        Message message = Message.builder()
                .conversation(conversation)
                .role(role)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        return messageRepository.save(message);
    }

    public List<Message> getConversationMessages(
            Conversation conversation
    ) {
        return messageRepository.findByConversation(conversation);
    }

}
