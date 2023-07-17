# Kafka Topic Change Notification with Slack

This is an application that notifies Slack messenger when a Kafka topic name has been changed.
Skip the explanation on how to install Kafka and create a Slack app.

## Requirements

* This project requires Java 8 or later.
* spring boot version 3.x
* kafka-clients 2.6.x
* slack-api-client 1.29.2

# Usage

* You can configure the Kafka bootstrap server and Slack details in the application.yml file.
  * ```
    monitoring:
      kafka:
        bootstrap-server: 127.0.0.1:9092
      file-dir: /kafka-topics/asis.txt
  
      slack:
        token: xoxb-xxxxxxxxxxxxxxxxxxxxxxxxxxxx
        channel-name: developer
    ```
* run application
  * build this project and run KafkaMonitoringApplication.class
  * [OR] java -jar kafka_monitoring*.jar  

* execute api
  * ```
      curl -X POST 'http://localhost:8080/kafka/topics/diff'
    ```

## output snapshot
![slack_notification](https://tnfhrnsss.github.io/docs/sub-projects/img/kafka_topic_slack_notification.png)

### blog reference

For further reference, please consider the following sections:

* [blog](https://tnfhrnsss.github.io/docs/sub-projects/kafka_topic_slack_notification/)
