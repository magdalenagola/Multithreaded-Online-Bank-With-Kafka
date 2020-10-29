package com.github.magdalenagola.multithreaded_online_bank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue
    private Integer id;
    @JoinColumn(name = "fromAccount")
    @OneToOne
    private Account fromAccount;
    @JoinColumn(name = "toAccount")
    @OneToOne
    private Account toAccount;
    private BigDecimal amount;
    private Date date;

    public Transaction(BigDecimal amount, Date date, Account fromAccount, Account toAccount) {
        this.amount = amount;
        this.date = date;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }
}
