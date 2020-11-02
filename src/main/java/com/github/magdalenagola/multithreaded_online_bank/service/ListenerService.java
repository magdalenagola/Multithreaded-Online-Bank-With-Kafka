package com.github.magdalenagola.multithreaded_online_bank.service;

import com.github.magdalenagola.multithreaded_online_bank.model.TransactionDTO;
import com.github.magdalenagola.multithreaded_online_bank.repository.AccountRepository;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListenerService {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private TransactionService transactionService;
    private AccountRepository accountRepository;

    public ListenerService(TransactionService transactionService, AccountRepository accountRepository) {
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
    }

    @KafkaListener(topics = "transaction", groupId = "test-consumer-group")
    public void listen(TransactionDTO transactionDTO) {
        Runnable consumer = new Consumer(transactionDTO, transactionService, accountRepository);
        executorService.submit(consumer);
    }
}
