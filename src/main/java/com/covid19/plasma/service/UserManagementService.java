package com.covid19.plasma.service;

import com.covid19.plasma.dao.UserRepoisitory;
import com.covid19.plasma.dao.entities.User;
import com.covid19.plasma.enums.UserType;
import com.covid19.plasma.exception.PhoneNumberAlreadyExistsException;
import com.covid19.plasma.security.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static java.util.Objects.nonNull;

@Service
public class UserManagementService implements UserDetailsService {

    @Autowired
    UserRepoisitory userRepoisitory;

    @Autowired
    PlasmaDonorService plasmaDonorService;

    @Autowired
    PlasmaRequestService plasmaRequestService;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) {
        User user = findByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new UsernameNotFoundException(phoneNumber);
        }
        return new AppUser(user.getPhoneNumber(), user.getOne_time_token(), true, true,
                true, true, new ArrayList<>(), getUserType(phoneNumber));
    }

    public boolean isPhoneNumberExists(String phoneNumber) {
        return nonNull(findByPhoneNumber(phoneNumber)) ? true : false;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    public User save(User user) {
        return userRepoisitory.save(user);
    }

    public User findByPhoneNumber(String phoneNumber) {
        User user;
        try {
            user = userRepoisitory.findByPhoneNumber(phoneNumber);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new PhoneNumberAlreadyExistsException("Multiple phone number exists");
        }
        return user;
    }

    private UserType getUserType(String phoneNumber) {
        if (isDonor(phoneNumber)) {
            return UserType.PLASMA_DONOR;
        } else if (isRequestor(phoneNumber)) {
            return UserType.PLASMA_REQUESTOR;
        }
        return UserType.UNKNOWN;
    }

    private boolean isDonor(String phoneNumber) {
        return nonNull(plasmaDonorService.findByPhoneNumber(phoneNumber)) ? true : false;
    }

    private boolean isRequestor(String phoneNumber) {
        return nonNull(plasmaRequestService.findByPhoneNumber(phoneNumber)) ? true : false;
    }
}
