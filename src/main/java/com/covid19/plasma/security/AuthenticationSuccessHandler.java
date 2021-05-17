package com.covid19.plasma.security;

import com.covid19.plasma.dao.entities.PlasmaDonor;
import com.covid19.plasma.dao.entities.PlasmaRequestor;
import com.covid19.plasma.dao.entities.User;
import com.covid19.plasma.service.PlasmaDonorService;
import com.covid19.plasma.service.PlasmaRequestService;
import com.covid19.plasma.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class AuthenticationSuccessHandler {

    @Autowired
    UserManagementService userManagementService;

    @Autowired
    PlasmaDonorService plasmaDonorService;

    @Autowired
    PlasmaRequestService plasmaRequestService;

    public void onSuccess(String phoneNumber) {
        setPhoneNumberAsVerified(phoneNumber);
    }

    private void setPhoneNumberAsVerified(String phoneNumber) {

        User dbUser = userManagementService.findByPhoneNumber(phoneNumber);
        if (nonNull(dbUser) && !dbUser.isPhoneNumberVerified()) {
            dbUser.setPhoneNumberVerified(true);
            userManagementService.save(dbUser);
        }

        PlasmaDonor plasmaDonor = plasmaDonorService.findByPhoneNumber(phoneNumber);
        if (nonNull(plasmaDonor) && isNull(plasmaDonor.getIsPhoneNumberVerified())) {
            plasmaDonor.setIsPhoneNumberVerified(true);
            plasmaDonorService.save(plasmaDonor);
        }

        PlasmaRequestor plasmaRequestor = plasmaRequestService.findByPhoneNumber(phoneNumber);
        if (nonNull(plasmaRequestor) && isNull(plasmaRequestor.getIsPhoneNumberVerified())) {
            plasmaRequestor.setIsPhoneNumberVerified(true);
            plasmaRequestService.save(plasmaRequestor);
        }
    }
}
