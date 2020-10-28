package com.github.magdalenagola.multithreaded_online_bank.transaction;

import com.github.magdalenagola.multithreaded_online_bank.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final ProducerService producerService;

    public TransactionController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public ResponseEntity<String> postTransaction(@RequestBody Transaction transaction){
        producerService.sendToKafka(transaction);
        return new ResponseEntity<>("Successfully send to kafka", HttpStatus.ACCEPTED);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illHandler(IllegalArgumentException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
