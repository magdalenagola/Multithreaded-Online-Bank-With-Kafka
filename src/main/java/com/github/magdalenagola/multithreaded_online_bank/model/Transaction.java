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
@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne
    private Account fromAccount;
    @OneToOne
    private Account toAccount;
    private BigDecimal amount;
    private Date date;
}
