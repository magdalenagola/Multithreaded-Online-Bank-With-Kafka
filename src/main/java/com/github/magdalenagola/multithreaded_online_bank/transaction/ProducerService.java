package com.github.magdalenagola.multithreaded_online_bank.transaction;

import com.github.magdalenagola.multithreaded_online_bank.model.TransactionDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class ProducerService {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final KafkaTemplate<String, TransactionDTO> kafkaTemplate;

    public ProducerService(KafkaTemplate<String, TransactionDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToKafka(TransactionDTO transaction)throws IllegalArgumentException{
        Runnable producer = new Producer(transaction, kafkaTemplate);
        Future<?> future = executorService.submit(producer);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException | CancellationException e) {
            throw new IllegalArgumentException("Sending to kafka failed");
        }
    }
}
