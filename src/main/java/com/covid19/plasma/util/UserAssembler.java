package com.covid19.plasma.util;

import com.covid19.plasma.dao.entities.PlasmaDonor;
import com.covid19.plasma.dao.entities.PlasmaRequestor;
import com.covid19.plasma.dao.entities.User;

import java.sql.Timestamp;

public class UserAssembler {

    public static User getUserEntityFromDonor(PlasmaDonor plasmaDonor) {
        User user = new User();
        user.setFirstName(plasmaDonor.getFirstName());
        user.setLastName(plasmaDonor.getLastName());
        user.setGender(plasmaDonor.getGender());
        user.setPhoneNumber(plasmaDonor.getPhoneNumber());
        user.setEmail(plasmaDonor.getEmail());
        user.setCity(plasmaDonor.getCity());
        user.setState(plasmaDonor.getState());
        user.setActive(true);
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return user;
    }

    public static User getUserEntityFromRequestor(PlasmaRequestor plasmaRequestor) {
        User user = new User();
        user.setFirstName(plasmaRequestor.getFirstName());
        user.setLastName(plasmaRequestor.getLastName());
        user.setGender(plasmaRequestor.getGender());
        user.setPhoneNumber(plasmaRequestor.getPhoneNumber());
        user.setEmail(plasmaRequestor.getEmail());
        user.setCity(plasmaRequestor.getCity());
        user.setState(plasmaRequestor.getState());
        user.setActive(true);
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return user;
    }
}
