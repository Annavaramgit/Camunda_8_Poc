package com.aus.corsafe.entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="userregistration")
@ToString

public class UserRegister {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Integer userId;
     private String firstName;
     private String lastName;
     private String email;
     private Long phoneNumber;
     private String password;
     private String abn;
     private String companyName;
     private String companyAddress;
     private String state;
     private Long postalCode;

     @OneToMany(mappedBy ="userRegister",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
     @JsonManagedReference
     private List<SecurityQuestionKey> securityQuestionList;


}
