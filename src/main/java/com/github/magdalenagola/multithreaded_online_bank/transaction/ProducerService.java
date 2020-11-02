package com.github.magdalenagola.multithreaded_online_bank.transaction;

import com.github.magdalenagola.multithreaded_online_bank.model.TransactionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.concurrent.*;

@Service
@Validated
public class ProducerService {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final KafkaTemplate<String, TransactionDTO> kafkaTemplate;

    public ProducerService(KafkaTemplate<String, TransactionDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public ResponseEntity<String> sendToKafka(@Valid TransactionDTO transaction){
        Runnable producer = new Producer(transaction, kafkaTemplate);
        Future<?> future = executorService.submit(producer);
        try {
            future.get();
            return new ResponseEntity<>("Successfully send to kafka", HttpStatus.ACCEPTED);
        } catch (InterruptedException | ExecutionException | CancellationException e) {
            return new ResponseEntity<>("Unable to send to kafka", HttpStatus.REQUEST_TIMEOUT);
        }
    }
}
