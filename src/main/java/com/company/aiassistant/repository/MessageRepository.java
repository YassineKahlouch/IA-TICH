package com.company.aiassistant.repository;


import com.company.aiassistant.entity.Conversation;
import com.company.aiassistant.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository
        extends JpaRepository<Message, Long> {

    List<Message> findByConversation(Conversation conversation);

}
