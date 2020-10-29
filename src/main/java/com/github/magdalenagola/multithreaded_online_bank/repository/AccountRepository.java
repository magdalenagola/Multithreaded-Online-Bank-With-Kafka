package com.github.magdalenagola.multithreaded_online_bank.repository;

import com.github.magdalenagola.multithreaded_online_bank.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String> {
}
