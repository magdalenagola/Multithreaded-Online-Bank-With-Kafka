package com.github.magdalenagola.multithreaded_online_bank.service;

import com.github.magdalenagola.multithreaded_online_bank.model.Account;
import com.github.magdalenagola.multithreaded_online_bank.model.Transaction;
import com.github.magdalenagola.multithreaded_online_bank.model.TransactionDTO;
import com.github.magdalenagola.multithreaded_online_bank.repository.AccountRepository;


class Consumer implements Runnable {

    private final TransactionDTO transactionDTO;
    private final TransactionService transactionService;
    private final AccountRepository accountRepository;

    public Consumer(TransactionDTO transactionDTO, TransactionService transactionService, AccountRepository accountRepository) {
        this.transactionDTO = transactionDTO;
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run() {
        if (!isTransactionIsValid(transactionDTO))
            throw new IllegalArgumentException("Balance on the account is too low to perform the transaction: " + transactionDTO.toString());
        transactionService.save(convert(transactionDTO));
    }

    private Transaction convert(TransactionDTO transactionDTO){
        Account fromAccount = accountRepository.findById(transactionDTO.getFromAccount())
                .orElseThrow(IllegalArgumentException::new);
        Account toAccount = accountRepository.findById(transactionDTO.getToAccount())
                .orElseThrow(IllegalArgumentException::new);
        return new Transaction(transactionDTO.getAmount(), transactionDTO.getDate(), fromAccount, toAccount);
    }

    private boolean isTransactionIsValid(TransactionDTO transactionDTO){
        Account fromAccount = accountRepository.findById(transactionDTO.getFromAccount())
                .orElseThrow(IllegalArgumentException::new);
        return fromAccount.getBalance().compareTo(transactionDTO.getAmount()) >= 0;
    }
}
