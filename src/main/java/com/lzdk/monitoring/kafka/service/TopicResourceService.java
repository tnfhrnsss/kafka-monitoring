package com.lzdk.monitoring.kafka.service;

import java.util.Properties;
import java.util.Set;

import org.apache.kafka.clients.admin.Admin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TopicResourceService {
    @Value("${monitoring.kafka.bootstrap-server:127.0.0.1:9092}")
    private String bootstrapServer;

    public Set find() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServer);
        Admin admin = Admin.create(properties);
        Set<String> topicSet;
        try {
            topicSet = admin.listTopics().names().get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        admin.close();
        return topicSet;
    }
}
