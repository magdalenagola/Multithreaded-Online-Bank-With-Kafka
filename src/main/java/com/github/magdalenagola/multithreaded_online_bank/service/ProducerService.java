package com.github.magdalenagola.multithreaded_online_bank.service;

import com.github.magdalenagola.multithreaded_online_bank.model.Reply;
import com.github.magdalenagola.multithreaded_online_bank.model.TransactionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.concurrent.*;

@Service
@Validated
public class ProducerService {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(16);
    private final ReplyingKafkaTemplate<String, TransactionDTO, Reply> kafkaTemplate;

    public ProducerService(ReplyingKafkaTemplate<String, TransactionDTO, Reply> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public ResponseEntity<String> sendToKafka(@Valid TransactionDTO transaction){
        Callable<Reply> producer = new Producer(transaction, kafkaTemplate);
        Future<Reply> future = executorService.submit(producer);
        try {
            Reply reply = future.get();
            switch(reply){
                case SUCCESS:
                    return new ResponseEntity<>("Successful transaction!\n"+transaction.toString(), HttpStatus.ACCEPTED);
                case FAILURE:
                    return new ResponseEntity<>("Balance on the account is too low to perform the transaction.\n"+transaction.toString(),HttpStatus.BAD_REQUEST);
                default:
                    return new ResponseEntity<>("Try again later", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (InterruptedException | ExecutionException | CancellationException e) {
            return new ResponseEntity<>("Unable to send to kafka\n"+transaction.toString(), HttpStatus.REQUEST_TIMEOUT);
        }
    }
}
