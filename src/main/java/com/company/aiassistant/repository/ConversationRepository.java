package com.company.aiassistant.repository;

import com.company.aiassistant.entity.Conversation;
import com.company.aiassistant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository
        extends JpaRepository<Conversation, Long> {

    List<Conversation> findByUser(User user);

}
