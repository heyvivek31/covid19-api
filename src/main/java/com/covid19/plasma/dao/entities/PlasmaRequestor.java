package com.covid19.plasma.dao.entities;

import com.covid19.plasma.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "plasma_requestor", indexes = {@Index(name = "idx_phone_number", columnList = "phone_number")})
public class PlasmaRequestor {
    String firstName;
    String lastName;
    String gender;
    @Column(name = "phone_number", nullable = false, unique = true)
    String phoneNumber;
    LocalDate dob;
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
    @Enumerated(EnumType.STRING)
    Status requestStatus;
    @OneToMany(mappedBy = "plasmaRequestor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<RequestorDonorMapperEntity> requestedDonors;
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
}
