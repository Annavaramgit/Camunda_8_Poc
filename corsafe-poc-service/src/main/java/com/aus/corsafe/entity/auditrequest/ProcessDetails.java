package com.aus.corsafe.entity.auditrequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="a")
public class ProcessDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Long phoneNumber;
    private String abn;
    private String companyName;
    private String companyAddress;
    private String state;
    private String postalCode;
    private long processInstanceKey;
    private long taskId;
    private Date createdAt;
    private Date updatedAt;
    private String assignee;
    private String implementation;
    private Integer orderId;


}
