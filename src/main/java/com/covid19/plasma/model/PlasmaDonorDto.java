package com.covid19.plasma.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class PlasmaDonorDto {
    private static final SimpleDateFormat dateFormat
            = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    String id;
    String firstName;
    String lastName;
    String gender;

    @NotNull
    @Size(min = 10, message = "Phone Number should have atleast 10 characters")
    String phoneNumber;
    int age;
    String email;

    @NotNull
    String bloodGroup;

    String hospitalName;
    String hospitalAddress;
    Date covidPostive;
    Date covidNegative;
    String city;
    String state;
    boolean isPhoneNumberVerified;
    boolean isEmailVerified;

    Date registeredOn;
}
