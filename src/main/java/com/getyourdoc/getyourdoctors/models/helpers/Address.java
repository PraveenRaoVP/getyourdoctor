package com.getyourdoc.getyourdoctors.models.helpers;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String doorNo;
    private String street;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
