package com.covid19.plasma.model;

import lombok.Data;
import org.apache.tomcat.jni.Local;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Data
public class PlasmaRequestDto {
    private static final SimpleDateFormat dateFormat
            = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    String id;

    String firstName;

    String lastName;

    String gender;

    @NotNull
    @Size(min = 10, message = "Phone Number should have atleast 10 characters")
    String phoneNumber;

    LocalDate dob;
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

    Date requestedOn;
}

