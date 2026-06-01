package com.company.aiassistant.controller;

import com.company.aiassistant.entity.Conversation;
import com.company.aiassistant.entity.Message;
import com.company.aiassistant.entity.User;
import com.company.aiassistant.service.ConversationService;
import com.company.aiassistant.service.MessageService;
import com.company.aiassistant.service.OpenAIService;
import com.company.aiassistant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MessageController {

    private final MessageService messageService;
    private final ConversationService conversationService;
    private final OpenAIService openAIService;
    private final UserService userService;

    @PostMapping("/{conversationId}")
    public Map<String, Object> sendMessage(
            @PathVariable Long conversationId,
            @RequestBody Map<String, String> request,
            Authentication authentication
    ) {

        User user = userService.findByEmail(
                authentication.getName()
        );

        Conversation conversation =
                conversationService.getConversationById(
                        conversationId
                );

        // Sécurité
        if (!conversation.getUser().getId().equals(
                user.getId()
        )) {

            throw new RuntimeException(
                    "Accès refusé"
            );
        }

        String userMessage =
                request.get("message");

        // Sauvegarde message utilisateur
        messageService.saveMessage(
                conversation,
                "user",
                userMessage
        );

        // Génération automatique du titre
        if (
                conversation.getTitle() != null
                        &&
                        conversation.getTitle().equalsIgnoreCase(
                                "Nouvelle discussion"
                        )
        ) {

            try {

                String generatedTitle =
                        openAIService.generateTitle(
                                userMessage
                        );

                if (
                        generatedTitle != null
                                &&
                                !generatedTitle.isBlank()
                ) {

                    generatedTitle =
                            generatedTitle
                                    .replace("\"", "")
                                    .replace("\n", "")
                                    .trim();

                    if (
                            generatedTitle.length()
                                    > 60
                    ) {

                        generatedTitle =
                                generatedTitle.substring(
                                        0,
                                        60
                                );
                    }

                    conversation.setTitle(
                            generatedTitle
                    );

                    conversationService.save(
                            conversation
                    );
                }

            } catch (Exception e) {

                System.out.println(
                        "Erreur génération titre : "
                                + e.getMessage()
                );
            }
        }

        // Réponse IA
        String aiResponse =
                openAIService.chat(
                        userMessage
                );

        Message aiMessage =
                messageService.saveMessage(
                        conversation,
                        "assistant",
                        aiResponse
                );

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "response",
                aiMessage.getContent()
        );

        response.put(
                "messageId",
                aiMessage.getId()
        );

        return response;
    }

    @GetMapping("/{conversationId}")
    public List<Message> getMessages(
            @PathVariable Long conversationId,
            Authentication authentication
    ) {

        User user = userService.findByEmail(
                authentication.getName()
        );

        Conversation conversation =
                conversationService.getConversationById(
                        conversationId
                );

        // Sécurité
        if (!conversation.getUser().getId().equals(
                user.getId()
        )) {

            throw new RuntimeException(
                    "Accès refusé"
            );
        }

        return messageService.getConversationMessages(
                conversation
        );
    }
}