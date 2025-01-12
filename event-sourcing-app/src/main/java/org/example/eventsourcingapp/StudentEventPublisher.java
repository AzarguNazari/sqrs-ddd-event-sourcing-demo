package org.example.eventsourcingapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentEventPublisher {
    private final ObjectMapper objectMapper;

    @Bean
    public Function<StudentEvent, Message> studentEvents() {
        return event -> {
            try {
                String eventJson = objectMapper.writeValueAsString(event);
                return MessageBuilder.withBody(eventJson.getBytes()).build();
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to serialize event", e);
            }
        };
    }
}

