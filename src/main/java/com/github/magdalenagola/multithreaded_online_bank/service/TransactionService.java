package com.github.magdalenagola.multithreaded_online_bank.service;

import com.github.magdalenagola.multithreaded_online_bank.model.Account;
import com.github.magdalenagola.multithreaded_online_bank.model.Transaction;
import com.github.magdalenagola.multithreaded_online_bank.repository.AccountRepository;
import com.github.magdalenagola.multithreaded_online_bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
class TransactionService {

    TransactionRepository transactionRepository;
    AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void save(Transaction transaction){
        transactionRepository.save(transaction);
        updateFromAccount(transaction.getFromAccount(), transaction.getAmount());
        updateToAccount(transaction.getToAccount(), transaction.getAmount());
    }

    private void updateFromAccount(Account fromAccount, BigDecimal transactionAmount){
        fromAccount.setBalance(fromAccount.getBalance().subtract(transactionAmount));
        accountRepository.save(fromAccount);
    }

    private void updateToAccount(Account toAccount, BigDecimal transactionAmount){
        toAccount.setBalance(toAccount.getBalance().add(transactionAmount));
        accountRepository.save(toAccount);
    }
}
