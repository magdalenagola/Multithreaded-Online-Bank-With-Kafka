package com.github.magdalenagola.multithreaded_online_bank.service;

import com.github.magdalenagola.multithreaded_online_bank.model.Account;
import com.github.magdalenagola.multithreaded_online_bank.model.Transaction;
import com.github.magdalenagola.multithreaded_online_bank.model.TransactionDTO;
import com.github.magdalenagola.multithreaded_online_bank.repository.AccountRepository;


public class Consumer implements Runnable {

    private TransactionDTO transactionDTO;
    private TransactionService transactionService;
    private AccountRepository accountRepository;

    public Consumer(TransactionDTO transactionDTO, TransactionService transactionService, AccountRepository accountRepository) {
        this.transactionDTO = transactionDTO;
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run() {
        if (!checkIfTransactionIsValid(transactionDTO)) throw new IllegalArgumentException("Balance on the account is to low to perform the transaction: " + transactionDTO.toString());
        transactionService.save(convert(transactionDTO));
    }

    private Transaction convert(TransactionDTO transactionDTO){
        Account fromAccount = accountRepository.findById(transactionDTO.getFromAccount()).orElseThrow(IllegalArgumentException::new);
        Account toAccount = accountRepository.findById(transactionDTO.getToAccount()).orElseThrow(IllegalArgumentException::new);
        return new Transaction(transactionDTO.getAmount(), transactionDTO.getDate(), fromAccount, toAccount);
    }

    private boolean checkIfTransactionIsValid(TransactionDTO transactionDTO){
        Account fromAccount = accountRepository.findById(transactionDTO.getFromAccount()).orElseThrow(IllegalArgumentException::new);
        return fromAccount.getBalance().compareTo(transactionDTO.getAmount()) >= 0;
    }
}