package com.covid19.plasma.service;

import com.covid19.plasma.dao.PlasmaRequestorRepoisitory;
import com.covid19.plasma.dao.entities.PlasmaRequestor;
import com.covid19.plasma.dao.entities.RequestorDonorMapperEntity;
import com.covid19.plasma.enums.Status;
import com.covid19.plasma.enums.UserType;
import com.covid19.plasma.exception.PlasmaDonorNotFound;
import com.covid19.plasma.exception.PlasmaRequestorNotFound;
import com.covid19.plasma.security.facade.IAuthenticationFacade;
import com.covid19.plasma.security.model.AppUser;
import com.covid19.plasma.util.UserAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class PlasmaRequestService {

    @Autowired
    PlasmaRequestorRepoisitory repository;
    
    @Autowired
    IAuthenticationFacade authenticationFacade;
    
    @Autowired
    private UserManagementService userManagementService;

    public List<PlasmaRequestor> getAllPlasmaRequests() {
        return (List<PlasmaRequestor>) repository.findAll();
    }

    public PlasmaRequestor getPlasmaRequestor(long id) throws PlasmaDonorNotFound {
        Optional<PlasmaRequestor> optionalRequestor = repository.findById(id);
        return optionalRequestor.orElseThrow(() -> new PlasmaRequestorNotFound("Plasma request with id: " + id + " not found"));
    }

    public void registerPlasmaRequest(PlasmaRequestor plasmaRequestor) {
        userManagementService.save(UserAssembler.getUserEntityFromRequestor(plasmaRequestor));

        plasmaRequestor.setRequestStatus(Status.ACTIVE);
        repository.save(plasmaRequestor);
    }

    public PlasmaRequestor findByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber);
    }

    public PlasmaRequestor save(PlasmaRequestor user) {
        return repository.save(user);
    }

    public List<RequestorDonorMapperEntity> getRequestedDonors() {
        List<RequestorDonorMapperEntity> requestedDonors = new ArrayList<>();
        AppUser plasmaUser = authenticationFacade.getPrincipal();

        if (isNull(plasmaUser) || plasmaUser.getUserType() == UserType.UNKNOWN) {
            return requestedDonors;
        }

        PlasmaRequestor plasmaRequestor = this.findByPhoneNumber(plasmaUser.getUsername());

        if (nonNull(plasmaRequestor)) {
            requestedDonors = plasmaRequestor.getRequestedDonors();
        }
        return requestedDonors;
    }
}