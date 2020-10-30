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
        convert(transactionData);
    }

    private void convert(String transactionData) {
        String[] transactionDataSplitted = transactionData.split(";");
        try {
            BigDecimal amount = new BigDecimal(transactionDataSplitted[0]);
            Date date = new SimpleDateFormat("dd/mm/yyyy").parse(transactionDataSplitted[1]);
            String fromAccount = Optional.ofNullable(transactionDataSplitted[2]).orElseThrow(IllegalArgumentException::new);
            String toAccount = Optional.ofNullable(transactionDataSplitted[3]).orElseThrow(IllegalArgumentException::new);
            TransactionDTO transaction = new TransactionDTO(amount, date, fromAccount, toAccount);
            synchronized (Converter.class) {
                logger.info("Transaction " + transaction.toString() + " was succesfully converted");
            }
            producerService.sendToKafka(transaction);
        } catch (Exception e) {
            synchronized (Converter.class) {
                logger.error("Transaction " + transactionData + " was not converted\n" + e.toString());
            }
        }
    }
}
