package com.github.magdalenagola.multithreaded_online_bank.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.magdalenagola.multithreaded_online_bank.service.validator.AccountNumber;
import com.github.magdalenagola.multithreaded_online_bank.service.validator.ExistingNumber;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionDTO implements Serializable {
    @Min(1)
    @NotNull
    private BigDecimal amount;
    @NotNull
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date date;
    @AccountNumber
    @ExistingNumber
    private String fromAccount;
    @AccountNumber
    @ExistingNumber
    private String toAccount;
}
