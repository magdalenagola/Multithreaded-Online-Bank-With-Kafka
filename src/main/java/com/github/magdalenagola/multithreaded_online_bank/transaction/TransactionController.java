package com.github.magdalenagola.multithreaded_online_bank.transaction;

import com.github.magdalenagola.multithreaded_online_bank.model.TransactionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final ProducerService producerService;

    public TransactionController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public ResponseEntity<String> postTransaction(@RequestBody TransactionDTO transaction) throws ParseException {
        producerService.sendToKafka(transaction);
        return new ResponseEntity<>("Successfully send to kafka", HttpStatus.ACCEPTED);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illHandler(IllegalArgumentException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
