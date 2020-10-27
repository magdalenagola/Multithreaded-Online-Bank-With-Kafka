package com.github.magdalenagola.multithreaded_online_bank.model;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class Account {

    @Id
    private String accountNumber;

    private BigDecimal balance;

    private Currency currency;

    private Type accountType;

    @ManyToOne
    private User user;

}
