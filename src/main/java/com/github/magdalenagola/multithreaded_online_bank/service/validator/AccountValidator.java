package com.github.magdalenagola.multithreaded_online_bank.service.validator;

import com.github.magdalenagola.multithreaded_online_bank.repository.AccountRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
class AccountValidator implements ConstraintValidator<ExistingNumber,String> {

    private final AccountRepository accountRepository;

    public AccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean isValid(String accountNumber, ConstraintValidatorContext constraintValidatorContext) {
        return accountRepository.existsById(accountNumber);
    }
}
