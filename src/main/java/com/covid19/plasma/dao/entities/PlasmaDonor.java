package com.covid19.plasma.dao.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "plasma_donor", indexes = {@Index(name = "idx_phone_number", columnList = "phone_number")})
public class PlasmaDonor {
    String firstName;
    String lastName;
    String gender;
    @Column(name = "phone_number", nullable = false)
    String phoneNumber;
    Integer age;
    String email;
    String bloodGroup;
    String hospitalName;
    String hospitalAddress;
    Date covidPostive;
    Date covidNegative;
    String city;
    String state;
    @Column(name = "created_at")
    Timestamp createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Timestamp updatedAt;
    @Column(name = "is_phone_number_verified", columnDefinition = "boolean default false")
    Boolean isPhoneNumberVerified;
    @Column(name = "is_email_verified", columnDefinition = "boolean default false")
    Boolean isEmailVerified;
    @Column(name = "status")
    String requestStatus;
    @Id
    @GeneratedValue
    private Long id;
}
