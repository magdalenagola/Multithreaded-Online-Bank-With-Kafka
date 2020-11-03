package com.github.magdalenagola.multithreaded_online_bank.repository;

import com.github.magdalenagola.multithreaded_online_bank.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

}
