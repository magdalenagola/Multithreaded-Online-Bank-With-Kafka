package com.github.magdalenagola.multithreaded_online_bank.service;

import com.github.magdalenagola.multithreaded_online_bank.model.TransactionDTO;
import com.github.magdalenagola.multithreaded_online_bank.repository.AccountRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class ConsumerService {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final TransactionService transactionService;
    private final AccountRepository accountRepository;

    public ConsumerService(TransactionService transactionService, AccountRepository accountRepository) {
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
    }

    @KafkaListener(topics = "transaction", groupId = "test-consumer-group")
    public void listen(TransactionDTO transactionDTO) {
        Runnable consumer = new Consumer(transactionDTO, transactionService, accountRepository);
        Future<?> future = executorService.submit(consumer);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException | CancellationException e) {
            e.printStackTrace();
        }
    }
}
