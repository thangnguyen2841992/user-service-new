package com.regain.user_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    NewTopic checkSendActiveTopic() {
        return new NewTopic("send-email-active-response",2,(short) 1);
    }
}
