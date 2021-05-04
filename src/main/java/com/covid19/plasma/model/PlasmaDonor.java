package com.covid19.plasma.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "plasma_donor")
public class PlasmaDonor {
    @Id
    @GeneratedValue
    private long id;
    String firstName;
    String lastName;
    String gender;
    @Column(name="phone_number", nullable = false)
    String phoneNumber;
    int age;
    String email;
    String bloodGroup;
    String hospitalName;
    String hospitalAddress;
    Date covidPostive;
    Date covidNegative;
    String city;
    String state;
    @Column(name="created_at")
    Timestamp createdAt;
    @Column(name="updated_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Timestamp updatedAt;
    @Column(name="is_phone_number_verified", columnDefinition="boolean default false")
    boolean isPhoneNumberVerified;
    @Column(name="is_email_verified", columnDefinition="boolean default false")
    boolean isEmailVerified;
    @Column(name="status")
    String requestStatus;
}
