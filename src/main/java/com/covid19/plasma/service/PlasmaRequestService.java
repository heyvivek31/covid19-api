package com.covid19.plasma.service;

import com.covid19.plasma.dao.PlasmaRequestorRepoisitory;
import com.covid19.plasma.dao.entities.PlasmaRequestor;
import com.covid19.plasma.exception.PlasmaDonorNotFound;
import com.covid19.plasma.exception.PlasmaRequestorNotFound;
import com.covid19.plasma.model.RequestStatus;
import com.covid19.plasma.util.UserAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlasmaRequestService {

    @Autowired
    PlasmaRequestorRepoisitory repository;
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

        plasmaRequestor.setRequestStatus(String.valueOf(RequestStatus.ACTIVE));
        repository.save(plasmaRequestor);
    }
}
