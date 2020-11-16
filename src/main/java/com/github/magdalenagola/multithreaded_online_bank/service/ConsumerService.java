package com.github.magdalenagola.multithreaded_online_bank.service;

import com.github.magdalenagola.multithreaded_online_bank.model.Reply;
import com.github.magdalenagola.multithreaded_online_bank.model.TransactionDTO;
import com.github.magdalenagola.multithreaded_online_bank.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
class ConsumerService {
    private static final Logger LOG = LoggerFactory.getLogger(ConsumerService.class);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(16);
    private final TransactionService transactionService;
    private final AccountRepository accountRepository;

    public ConsumerService(TransactionService transactionService, AccountRepository accountRepository) {
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
    }

    @KafkaListener(topics = "transaction", groupId = "test-consumer-group", containerFactory = "kafkaListenerContainerFactory")
    @SendTo("transaction-reply")
    public Reply listen(TransactionDTO transactionDTO) {
        Runnable consumer = new Consumer(transactionDTO, transactionService, accountRepository);
        Future<?> future = executorService.submit(consumer);
        try {
            future.get();
            LOG.info(String.format("Transaction successfully saved to database! \n%s!", transactionDTO.toString()));
            return Reply.SUCCESS;
        } catch (InterruptedException | ExecutionException | CancellationException e) {
            LOG.warn(String.format("Transaction failure! %s \n  %s!", transactionDTO.toString(),e.getMessage()));
            return Reply.FAILURE;
        }
    }
}
