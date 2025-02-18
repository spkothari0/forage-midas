package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaProducer {
    private final String topic;
    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public KafkaProducer(@Value("${general.kafka-topic}") String topic, KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String transactionLine) {
        transactionLine = transactionLine.trim();
        String[] transactionData = transactionLine.split(", ");
        CompletableFuture<SendResult<String, Transaction>> result = kafkaTemplate.send(topic, new Transaction(Long.parseLong(transactionData[0]), Long.parseLong(transactionData[1]), Float.parseFloat(transactionData[2])));
        if(result.isDone()) {
            result.whenComplete((sendResult, ex) -> {
                if (ex != null) {
                    System.err.println("Error sending transaction to Kafka: " + ex.getMessage());
                } else {
                    System.out.println("Transaction sent to Kafka: " + sendResult.toString());
                }
            });
        }
        else {
            System.out.println("Transaction sending to Kafka not completed...");
        }
    }
}