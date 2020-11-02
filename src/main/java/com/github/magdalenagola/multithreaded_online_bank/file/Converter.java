package com.github.magdalenagola.multithreaded_online_bank.file;


import com.github.magdalenagola.multithreaded_online_bank.model.TransactionDTO;
import com.github.magdalenagola.multithreaded_online_bank.transaction.ProducerService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


public class Converter implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Converter.class);
    private final String transactionData;
    private final ProducerService producerService;

    public Converter(ProducerService producerService, String transactionData) {
        this.producerService = producerService;
        this.transactionData = transactionData;
    }

    @Override
    public void run() {
        String[] transactionDataSplit = transactionData.split(";");
        TransactionDTO transaction;
        try {
            BigDecimal amount = new BigDecimal(transactionDataSplit[0]);
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(transactionDataSplit[1]);
            String fromAccount = Optional.ofNullable(transactionDataSplit[2]).orElseThrow(IllegalArgumentException::new);
            String toAccount = Optional.ofNullable(transactionDataSplit[3]).orElseThrow(IllegalArgumentException::new);
            transaction = new TransactionDTO(amount, date, fromAccount, toAccount);
            synchronized (Converter.class) {
                logger.info("Transaction " + transaction.toString() + " was successfully converted");
            }
        } catch (Exception e) {
            synchronized (Converter.class) {
                logger.error("Transaction " + transactionData + " was not converted\n" + e.toString());
            }
            return;
        }
        producerService.sendToKafka(transaction);
    }
}
