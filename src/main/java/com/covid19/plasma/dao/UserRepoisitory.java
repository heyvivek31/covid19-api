package com.covid19.plasma.dao;

import com.covid19.plasma.dao.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepoisitory extends CrudRepository<User, Long> {
    public User findByPhoneNumber(String phoneNumber);
}
