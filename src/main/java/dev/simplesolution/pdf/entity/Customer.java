package dev.simplesolution.pdf.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Customer {
    private String companyName;
    private String contactName;
    private String address;
    private String email;
    private String phone;

}
