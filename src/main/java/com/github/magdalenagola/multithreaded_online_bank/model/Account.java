package com.github.magdalenagola.multithreaded_online_bank.model;

import lombok.*;
import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class Account {

    @Id
    private long accountNumber;

    private Currency currency;

    private Type accountType;

    @ManyToOne
    private User user;
}
