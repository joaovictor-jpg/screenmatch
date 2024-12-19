package br.com.jota.screenmetch.screenmetch.services;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class ConsultaGemini {
    public static String obterTraducao(String texto, String openAIToken) {
        ChatLanguageModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(openAIToken)
                .modelName("gemini-1.5-flash")
                .maxOutputTokens(1000)
                .temperature(0.7)
                .build();

        return gemini.generate("traduza para o português o texto: " + texto);
    }
}
