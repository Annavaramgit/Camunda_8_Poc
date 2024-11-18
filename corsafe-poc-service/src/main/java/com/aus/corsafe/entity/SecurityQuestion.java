package com.aus.corsafe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class SecurityQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String question;
}
