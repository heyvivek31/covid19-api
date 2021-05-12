package com.covid19.plasma.dao;

import com.covid19.plasma.dao.entities.PlasmaRequestor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlasmaRequestorRepoisitory extends CrudRepository<PlasmaRequestor, Long> {
    PlasmaRequestor findByPhoneNumber(String phoneNumber);
}
