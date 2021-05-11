package com.covid19.plasma.service;

import com.covid19.plasma.dao.PlasmaDonorRepoisitory;
import com.covid19.plasma.dao.entities.PlasmaDonor;
import com.covid19.plasma.enums.Status;
import com.covid19.plasma.exception.PlasmaDonorNotFound;
import com.covid19.plasma.util.UserAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlasmaDonorService {

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private PlasmaDonorRepoisitory repository;

    public List<PlasmaDonor> getAllPlasmaDonors() {
        return (List<PlasmaDonor>) repository.findAll();
    }

    public PlasmaDonor getPlasmaDonor(long id) throws PlasmaDonorNotFound {
        Optional<PlasmaDonor> optionalDonor = repository.findById(id);
        return optionalDonor.orElseThrow(() -> new PlasmaDonorNotFound("Plasma donor with id: " + id + " not found"));
    }

    public void registerPlasmaDonor(PlasmaDonor plasmaDonor) {
        userManagementService.save(UserAssembler.getUserEntityFromDonor(plasmaDonor));

        plasmaDonor.setRequestStatus(Status.ACTIVE);
        repository.save(plasmaDonor);
    }

    public PlasmaDonor findByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber);
    }

    public Optional<PlasmaDonor> findById(Long id) {
        return repository.findById(id);
    }

    public PlasmaDonor save(PlasmaDonor user) {
        return repository.save(user);
    }
}
