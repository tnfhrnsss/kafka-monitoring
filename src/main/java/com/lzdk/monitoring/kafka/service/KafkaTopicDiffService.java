package com.lzdk.monitoring.kafka.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lzdk.monitoring.slack.service.SlackSendMessageService;
import com.lzdk.monitoring.utils.FileMaker;
import com.lzdk.monitoring.utils.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaTopicDiffService {
    @Value("${monitoring.file-dir:/kafka-topics/asis.txt}")
    private String fileDir;

    private final TopicResourceService topicResourceService;

    private final SlackSendMessageService slackSendMessageService;

    public void diff() {
        try {
            diff(topicResourceService.find());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void diff(Set<String> topicSet) {
        Path path = FileMaker.find(fileDir);

        if (path.toFile().exists()) {
            try (Stream<String> stream = Files.lines(path)) {
                String asisTopics = stream.collect(Collectors.joining(""));
                Set<String> topics = JsonParser.fromJson(asisTopics, Set.class);

                List<String> oldOne = topics.stream().toList();
                log.debug("old topics size : " + oldOne.size());

                List newOne = getNewOne(topicSet, oldOne);
                List<String> delOne = getDelOne(topicSet, oldOne);

                log.debug("newone : " + newOne);
                log.debug("delone : " + delOne);

                // send slack alarm
                sendMessage(newOne, delOne);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileMaker.save(JsonParser.toJson(topicSet), fileDir);
    }

    @NotNull
    private static List<String> getDelOne(Set<String> topicSet, List<String> oldOne) {
        return oldOne.stream()
            .filter(topic -> topicSet.stream().noneMatch(eachOne -> topic.trim().equals(eachOne.trim())))
            .collect(Collectors.toList());
    }

    @NotNull
    private static List getNewOne(Set<String> topicSet, List<String> oldOne) {
        return topicSet.stream()
            .filter(topic -> oldOne.stream().noneMatch(eachOne -> topic.trim().equals(eachOne.trim())))
            .collect(Collectors.toList());
    }

    private void sendMessage(List newOne, List<String> delOne) {
        if (!CollectionUtils.isEmpty(newOne)) {
            slackSendMessageService.send("new topic! : " + newOne);
        }

        if (!CollectionUtils.isEmpty(delOne)) {
            slackSendMessageService.send("deleted topic! : " + delOne);
        }
    }
}
