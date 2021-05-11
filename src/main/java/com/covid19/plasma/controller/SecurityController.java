package com.covid19.plasma.controller;

import com.covid19.plasma.exception.PhoneNumberNotFoundException;
import com.covid19.plasma.exception.PlasmaException;
import com.covid19.plasma.exception.TokenException;
import com.covid19.plasma.model.AuthenticationRequest;
import com.covid19.plasma.model.AuthenticationResponse;
import com.covid19.plasma.service.TokenGenerator;
import com.covid19.plasma.service.UserManagementService;
import com.covid19.plasma.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private JwtUtil jwtUtil;

/*    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getPhoneNumber(), ""));
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorrect  phonenumber");
        }

        final UserDetails userDetails = userManagementService.loadUserByUsername(authenticationRequest.getPhoneNumber());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }*/

    @RequestMapping(value = "/generate/otp", method = RequestMethod.POST)
    public String generateOTP(@RequestBody AuthenticationRequest authenticationRequest) throws PlasmaException {
        final boolean isUser = userManagementService.isPhoneNumberExists(authenticationRequest.getPhoneNumber());
        if (!isUser) {
            throw new PhoneNumberNotFoundException("phone number not found");
        }
        String token = tokenGenerator.generateToken(authenticationRequest);
        return token;
    }

    @RequestMapping(value = "/validate/otp", method = RequestMethod.POST)
    public ResponseEntity<?> validateOTP(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getPhoneNumber(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new TokenException("invalid otp");
        }

        final UserDetails userDetails = userManagementService.loadUserByUsername(authenticationRequest.getPhoneNumber());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
