package com.covid19.plasma.dao.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "user", indexes = {@Index(name = "idx_phone_number", columnList = "phone_number")})
public class User {

    String username;
    String password;
    String firstName;
    String lastName;
    @Column(name = "phone_number", nullable = false)
    String phoneNumber;
    String email;
    String gender;
    LocalDate dob;
    String city;
    String state;
    @Column(name = "created_at")
    Timestamp createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Timestamp updatedAt;
    @Column(name = "is_phone_number_verified", columnDefinition = "boolean default false")
    boolean isPhoneNumberVerified;
    @Column(name = "is_email_verified", columnDefinition = "boolean default false")
    boolean isEmailVerified;
    @Column(name = "status", columnDefinition = "boolean default true")
    boolean isActive;
    String one_time_token;
    Timestamp token_requested_time;
    @Id
    @GeneratedValue
    private Long id;
}
