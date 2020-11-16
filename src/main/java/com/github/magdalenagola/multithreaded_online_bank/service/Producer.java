package com.github.magdalenagola.multithreaded_online_bank.service;

import com.github.magdalenagola.multithreaded_online_bank.model.Reply;
import com.github.magdalenagola.multithreaded_online_bank.model.TransactionDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

class Producer implements Callable<Reply> {
    private static final Logger LOG = LoggerFactory.getLogger(Producer.class);
    private final String TOPIC = "transaction";
    private final ReplyingKafkaTemplate<String, TransactionDTO, Reply> kafkaTemplate;
    private final TransactionDTO transaction;

    public Producer(TransactionDTO transaction, ReplyingKafkaTemplate<String, TransactionDTO, Reply> kafkaTemplate) {
        this.transaction = transaction;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Reply call() {
        ProducerRecord<String, TransactionDTO> producerRecord = new ProducerRecord<>(TOPIC, transaction);
        producerRecord.headers().add(KafkaHeaders.REPLY_TOPIC,"transaction-reply".getBytes());
        RequestReplyFuture<String, TransactionDTO, Reply> replyFuture = kafkaTemplate.sendAndReceive(producerRecord);
        ListenableFuture<SendResult<String, TransactionDTO>> future = replyFuture.getSendFuture();
        future.addCallback(new ListenableFutureCallback<SendResult<String, TransactionDTO>>() {
            @Override
            public void onSuccess(SendResult<String, TransactionDTO> result) {
                LOG.info("Transaction [{}] delivered with offset {}",
                        transaction.toString(),
                        result.getRecordMetadata().offset());

            }

            @Override
            public void onFailure(Throwable ex) {
                LOG.warn("Unable to deliver transaction [{}] ! {}",
                        transaction.toString(),
                        ex.getMessage());

            }
        });

        try {
            ConsumerRecord<String, Reply> stringReplyConsumerRecord = replyFuture.get();
            return stringReplyConsumerRecord.value();
        } catch (InterruptedException | ExecutionException | CancellationException e) {
            throw new RuntimeException("Future failure");
        }
    }
}
