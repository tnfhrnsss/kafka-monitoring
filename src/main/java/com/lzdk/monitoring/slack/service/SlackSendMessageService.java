package com.lzdk.monitoring.slack.service;

import java.io.IOException;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackSendMessageService {
    private static String token = "";

    @Value("${monitoring.slack.token:}")
    public void setToken(String value) {
        token = value;
    }

    private final SlackConversationService slackConversationService;

    static void publishMessage(String channelId, String message) {
        var client = Slack.getInstance().methods();
        try {
            var result = client.chatPostMessage(r -> r
                .token(token)
                .channel(channelId)
                .text(message)
            );
            log.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            log.error("error: {}", e.getMessage(), e);
        }
    }

    public void send(String message) {
        String channelId = slackConversationService.find();
        publishMessage(channelId, message);
    }
}
