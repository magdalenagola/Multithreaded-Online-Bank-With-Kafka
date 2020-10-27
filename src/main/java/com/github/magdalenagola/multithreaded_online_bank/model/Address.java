package com.github.magdalenagola.multithreaded_online_bank.model;

import lombok.*;
import javax.persistence.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class Address {
    private String city;
    private String street;
    private Integer houseNumber;
}
