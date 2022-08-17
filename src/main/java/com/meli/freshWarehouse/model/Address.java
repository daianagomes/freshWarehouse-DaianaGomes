package com.meli.freshWarehouse.model;

import javax.persistence.Column;

public class Address {

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "number")
    private Integer number;
}