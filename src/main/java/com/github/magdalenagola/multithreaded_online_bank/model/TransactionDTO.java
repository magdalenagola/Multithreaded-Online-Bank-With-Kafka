package com.github.magdalenagola.multithreaded_online_bank.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.magdalenagola.multithreaded_online_bank.validator.AccountNumber;
import com.github.magdalenagola.multithreaded_online_bank.validator.ExistingNumber;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

    public TransactionDTO() {
    }

    public TransactionDTO(BigDecimal amount, Date date, String fromAccount, String toAccount) {
        this.amount = amount;
        this.date = date;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "amount=" + amount +
                ", date=" + date +
                ", fromAccount='" + fromAccount + '\'' +
                ", toAccount='" + toAccount + '\'' +
                '}';
    }
}
