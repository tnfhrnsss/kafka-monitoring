package com.lzdk.monitoring.kafka.controller;

import com.lzdk.monitoring.kafka.service.KafkaTopicDiffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("kafka")
public class KafkaTopicDiffMonitorController {
    private final KafkaTopicDiffService kafkaTopicDiffService;

    @PostMapping("topics/diff")
    public void diff() {
        kafkaTopicDiffService.diff();
    }
}
