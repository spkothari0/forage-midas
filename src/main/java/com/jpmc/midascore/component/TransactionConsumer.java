package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionConsumer {
    private final TransactionHandler transactionHandler;

    @KafkaListener(topics = "${general.kafka-topic}")
    public void consume(ConsumerRecord<String, Transaction> record) {
        transactionHandler.handleTransaction(record.value());
    }

}
