package com.hivemind.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    Long permanentAccountNumber;
    String loginId;
    String password;
    boolean isNewCustomer;
    String role;
    String firstname;
    String lastname;
    String uniqueIdentifier;
    String email;
    int birthDay;
    String birthMonth;
    int birthYear;
    String street;
    String city;
    String state;
    String areaCode;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<Account> account;

    @JsonIgnore
    @ManyToMany(mappedBy = "linkedCustomer", fetch = FetchType.EAGER)
    List<Account> linkedAccounts;
}
