package com.github.magdalenagola.multithreaded_online_bank.file;


import com.github.magdalenagola.multithreaded_online_bank.model.Account;
import com.github.magdalenagola.multithreaded_online_bank.model.Transaction;
import com.github.magdalenagola.multithreaded_online_bank.repository.AccountRepository;
import com.github.magdalenagola.multithreaded_online_bank.transaction.ProducerService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Converter implements Runnable {

    private Logger logger = LoggerFactory.getLogger(Converter.class); //make static and synchronize
    private final String transactionData;
    private final ProducerService producerService;

    public Converter(ProducerService producerService, String transactionData) {
        this.producerService = producerService;
        this.transactionData = transactionData;
    }

    @Override
    public void run() {
        try {
            convert(transactionData);
            logger.info("Transaction is being converted");
        } catch (Exception e) {
            logger.warn("Transaction cannot be converted " + e.getCause());
            e.printStackTrace();
        }
    }

    private void convert(String transactionData) throws ParseException {
        String[] transactionDataSplitted = transactionData.split(";");
        BigDecimal amount = new BigDecimal(transactionDataSplitted[0]);
        Date date = new SimpleDateFormat("dd/mm/yyyy").parse(transactionDataSplitted[1]);
        String fromAccount = transactionDataSplitted[2];
        String toAccount = transactionDataSplitted[3];
        TransactionDTO transaction = new TransactionDTO(amount, date, fromAccount, toAccount);
        producerService.sendToKafka(transaction);
    }
}
