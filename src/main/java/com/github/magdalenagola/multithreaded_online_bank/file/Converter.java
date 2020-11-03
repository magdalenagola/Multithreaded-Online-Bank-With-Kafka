package com.github.magdalenagola.multithreaded_online_bank.file;


import com.github.magdalenagola.multithreaded_online_bank.model.TransactionDTO;
import com.github.magdalenagola.multithreaded_online_bank.service.ProducerService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;


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
        String[] attributes = transactionData.split(";");
        Optional<TransactionDTO> transactionPossibly = getTransactionDTO(attributes);
        transactionPossibly.ifPresent(producerService::sendToKafka);
    }

    private Optional<TransactionDTO> getTransactionDTO(String[] attributes) {
        TransactionDTO transaction = null;
        try {
            BigDecimal amount = new BigDecimal(attributes[0]);
            Date date = getDate(attributes[1]);
            String fromAccount = Optional.ofNullable(attributes[2]).orElseThrow(IllegalArgumentException::new);
            String toAccount = Optional.ofNullable(attributes[3]).orElseThrow(IllegalArgumentException::new);
            transaction = new TransactionDTO(amount, date, fromAccount, toAccount);
            synchronized (Converter.class) {
                logger.info("Transaction " + transaction.toString() + " was successfully converted");
            }
        } catch (Exception e) {
            synchronized (Converter.class) {
                logger.error("Transaction " + transactionData + " was not converted\n" + e.toString());
            }
        }
        return Optional.ofNullable(transaction);
    }

    private Date getDate(String dateAttribute) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.parse(dateAttribute);
    }
}
