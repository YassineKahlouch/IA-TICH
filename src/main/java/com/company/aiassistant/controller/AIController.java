package com.company.aiassistant.controller;


import com.company.aiassistant.dto.ChatRequest;
import com.company.aiassistant.dto.ChatResponse;
import com.company.aiassistant.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final OpenAIService openAIService;

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {

        String response =
                openAIService.chat(request.getMessage());

        return new ChatResponse(response);
    }
}
