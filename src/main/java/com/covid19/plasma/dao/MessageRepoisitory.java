package com.covid19.plasma.dao;

import com.covid19.plasma.dao.entities.MessageLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface MessageRepoisitory extends CrudRepository<MessageLog, Long> {
    long countByCreatedAtGreaterThanEqualAndPhoneNumber(Timestamp timestamp, String phoneNumber);
}
