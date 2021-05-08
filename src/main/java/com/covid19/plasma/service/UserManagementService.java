package com.covid19.plasma.service;

import com.covid19.plasma.dao.UserRepoisitory;
import com.covid19.plasma.dao.entities.User;
import com.covid19.plasma.exception.DuplicatePhoneNumberFoundException;
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

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) {
        User user = findByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new UsernameNotFoundException(phoneNumber);
        }
        return new org.springframework.security.core.userdetails.User(user.getPhoneNumber(), user.getOne_time_token(), new ArrayList<>());
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
            throw new DuplicatePhoneNumberFoundException("Multiple phone number exists");
        }
        return user;
    }
}
