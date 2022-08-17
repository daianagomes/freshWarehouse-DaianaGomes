package com.meli.freshWarehouse.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Buyer {
    public Buyer(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotEmpty(message = "Buyer name cannot be empty.")
    @Size(max = 50, message = "Buyer name can't exceed 50 characters.")
    private String name;

    @Column
    private String zipCode;

    @OneToMany(mappedBy = "buyer")
    @JsonIgnore
    private Set<PurchaseOrder> purchaseOrders;
}
