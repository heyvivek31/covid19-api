package com.covid19.plasma.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Data
public class PlasmaDonorDto {
    private static final SimpleDateFormat dateFormat
            = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    String id;
    @NotEmpty
    String firstName;
    String lastName;
    String gender;

    @NotEmpty
    @Size(min = 10, message = "Phone Number should have atleast 10 characters")
    String phoneNumber;

    LocalDate dob;
    int age;
    
    @Email(message = "Email should be valid")
    String email;

    @NotNull
    String bloodGroup;

    String hospitalName;
    String hospitalAddress;
    Date covidPostive;
    Date covidNegative;
    @NotEmpty
    String city;
    @NotEmpty
    String state;
    boolean isPhoneNumberVerified;
    boolean isEmailVerified;

    Date registeredOn;

    ReceivedPlasmaRequest requestorRequestPlasma;
}
