package com.company.aiassistant.controller;

import com.company.aiassistant.entity.Conversation;
import com.company.aiassistant.entity.User;
import com.company.aiassistant.service.ConversationService;
import com.company.aiassistant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ConversationController {

    private final ConversationService conversationService;
    private final UserService userService;

    // =========================
    // CREATE CONVERSATION
    // =========================

    @PostMapping
    public Conversation createConversation(
            Authentication authentication
    ) {

        User user = userService.findByEmail(
                authentication.getName()
        );

        return conversationService.createConversation(user);
    }

    // =========================
    // GET USER CONVERSATIONS
    // =========================

    @GetMapping
    public List<Conversation> getUserConversations(
            Authentication authentication
    ) {

        User user = userService.findByEmail(
                authentication.getName()
        );

        return conversationService.getUserConversations(user);
    }

}