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
    @Getter
    @Setter
    private long accountNumber;

    @Getter
    @Setter
    private Currency currency;

    @Getter
    @Setter
    private Type accountType;

    @ManyToOne
    @Getter
    @Setter
    private User user;
}
