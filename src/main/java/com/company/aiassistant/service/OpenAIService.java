package com.company.aiassistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    @Value("${openrouter.api.key}")
    private String apiKey;

    public String chat(String message) {

        WebClient webClient = WebClient.builder()
                .baseUrl("https://openrouter.ai/api/v1")
                .defaultHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + apiKey
                )
                .build();

        Map<String, Object> requestBody =
                Map.of(

                        "model",
                        "openai/gpt-3.5-turbo",

                        "messages",
                        List.of(
                                Map.of(
                                        "role", "user",
                                        "content", message
                                )
                        )

                );

        Map response = webClient.post()
                .uri("/chat/completions")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        List choices =
                (List) response.get("choices");

        Map choice =
                (Map) choices.get(0);

        Map messageMap =
                (Map) choice.get("message");

        return messageMap
                .get("content")
                .toString();
    }

    public String generateTitle(
            String firstMessage
    ) {

        String prompt =
                """
                Génère uniquement un titre court
                de maximum 5 mots.
    
                Pas de guillemets.
                Pas de phrase complète.
                Pas de ponctuation.
    
                Message :
                """ + firstMessage;

        return chat(prompt);
    }
}