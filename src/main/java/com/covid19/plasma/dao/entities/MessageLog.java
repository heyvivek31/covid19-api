package com.covid19.plasma.dao.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "message_log", indexes = {@Index(name = "idx_created_at", columnList = "created_at")})
public class MessageLog {
    
    String errorCode;
    String errorMessage;
    @Column(name = "phone_number")
    String phoneNumber;
    @Column(name = "created_at")
    Timestamp createdAt;
    @Id
    @GeneratedValue
    private Long id;
}
