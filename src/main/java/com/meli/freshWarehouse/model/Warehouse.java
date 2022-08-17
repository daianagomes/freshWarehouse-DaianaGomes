package com.meli.freshWarehouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "number")
    private Integer number;

    @Column(name="zipCode")
    private String zipCode;

    @OneToMany(mappedBy = "warehouse")
    @JsonIgnore
    private Set<Representative> listRepresentative;

    @OneToMany(mappedBy = "warehouse")
    @JsonIgnore
    private Set<Section> listSection;


    public Warehouse(String zipCode, String address, String city, String state, String country, Integer number) {
        this.zipCode = zipCode;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.number = number;
    }

}
