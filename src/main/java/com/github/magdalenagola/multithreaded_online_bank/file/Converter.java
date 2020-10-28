package com.github.magdalenagola.multithreaded_online_bank.file;


import com.github.magdalenagola.multithreaded_online_bank.model.Account;
import com.github.magdalenagola.multithreaded_online_bank.model.Transaction;
import com.github.magdalenagola.multithreaded_online_bank.repository.AccountRepository;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class Converter implements Runnable {

    private Logger logger = (Logger) LoggerFactory.getLogger(Converter.class);
    private final String transactionData;
    private final ProducerService producerService;
    private final AccountRepository accountRepository;

    public Converter(ProducerService producerService, String transactionData, AccountRepository accountRepository) {
        this.producerService = producerService;
        this.transactionData = transactionData;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run() {
        try {
            convert(transactionData);
            logger.info("Transaction is being converted");
        } catch (Exception e) {
            logger.warning("Transaction cannot be converted");
        }
    }

    private void convert(String transactionData) throws ParseException {
        String[] transactionDataSplitted = transactionData.split(";");
        BigDecimal amount = new BigDecimal(transactionDataSplitted[0]);
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(transactionDataSplitted[1]);
        Account fromAccount = accountRepository.findById(transactionDataSplitted[2]).orElseThrow(IllegalArgumentException::new);
        Account toAccount = accountRepository.findById(transactionDataSplitted[3]).orElseThrow(IllegalArgumentException::new);
        Transaction transaction = new Transaction(amount, date, fromAccount, toAccount);
        producerService.sendToKafka(transaction);
    }
}
