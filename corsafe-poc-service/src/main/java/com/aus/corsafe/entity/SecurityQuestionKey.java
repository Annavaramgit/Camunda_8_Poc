package com.aus.corsafe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import lombok.ToString;

@Data
@Entity
@ToString
public class SecurityQuestionKey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer questionId;
    private String answer;
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference
    private UserRegister userRegister;
}
