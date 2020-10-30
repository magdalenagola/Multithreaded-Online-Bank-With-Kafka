package com.github.magdalenagola.multithreaded_online_bank.transaction;

import com.github.magdalenagola.multithreaded_online_bank.model.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class Producer implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(Producer.class);
    private final String TOPIC = "transaction";
    private final KafkaTemplate<String,TransactionDTO> kafkaTemplate;
    private final TransactionDTO transaction;

    public Producer(TransactionDTO transaction, KafkaTemplate<String, TransactionDTO> kafkaTemplate) {
        this.transaction = transaction;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void run()throws IllegalArgumentException {
        ListenableFuture<SendResult<String, TransactionDTO>> future = kafkaTemplate.send(TOPIC, transaction);
        future.addCallback(new ListenableFutureCallback<SendResult<String, TransactionDTO>>() {
            @Override
            public void onSuccess(SendResult<String, TransactionDTO> result) {
                synchronized (Producer.class) {
                    LOG.info("Transaction [{}] delivered with offset {}",
                            transaction.toString(),
                            result.getRecordMetadata().offset());
                }
            }
            @Override
            public void onFailure(Throwable ex)throws IllegalArgumentException {
                synchronized (Producer.class) {
                    LOG.warn("Unable to deliver transaction [{}] ! {}",
                            transaction.toString(),
                            ex.getMessage());
                }
            }
        });
    }
}