package com.covid19.plasma.dao.entities;

import com.covid19.plasma.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "plasma_donor", indexes = {@Index(name = "idx_phone_number", columnList = "phone_number")})
public class PlasmaDonor<List> {
    String firstName;
    String lastName;
    String gender;
    @Column(name = "phone_number", nullable = false, unique = true)
    String phoneNumber;
    Date dob;
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
    @OneToMany(mappedBy = "plasmaDonor")
    java.util.List<RequestorDonorMapperEntity> offerRequestors;
    @Id
    @GeneratedValue
    private Long id;
}
