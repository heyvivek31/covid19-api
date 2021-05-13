package com.covid19.plasma.service;

import com.covid19.plasma.dao.entities.User;
import com.covid19.plasma.model.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Random;

@Service
public class TokenGenerator {

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private OtpService otpService;

    public String generateToken(AuthenticationRequest authenticationRequest) throws IOException {
        String token = getToken();
        otpService.sendSms(authenticationRequest.getPhoneNumber(), "Use OTP " + token + " to login to your Raktsetu Account. Do not share it with anyone.");
        updateUserToken(authenticationRequest.getPhoneNumber(), token);
        return token;
    }

    private String getToken() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    private void updateUserToken(String phoneNumber, String token) {
        User user = userManagementService.findByPhoneNumber(phoneNumber);
        user.setOne_time_token(token);
        user.setToken_requested_time(new Timestamp(System.currentTimeMillis()));
        userManagementService.save(user);
    }
}
