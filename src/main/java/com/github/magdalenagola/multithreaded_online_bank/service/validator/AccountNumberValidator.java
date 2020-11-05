package com.github.magdalenagola.multithreaded_online_bank.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class AccountNumberValidator implements ConstraintValidator<AccountNumber,String> {
    @Override
    public boolean isValid(String accountNumber, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile("^([A-Z]{2}[ \\-]?[0-9]{2})(?=(?:[ \\-]?[A-Z0-9]){9,30}$)((?:[ \\-]?[A-Z0-9]{3,5}){2,7})([ \\-]?[A-Z0-9]{1,3})?$");
        Matcher matcher = pattern.matcher(accountNumber);
        return matcher.matches();
    }
}
