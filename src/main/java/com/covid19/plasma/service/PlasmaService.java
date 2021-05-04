package com.covid19.plasma.service;

import com.covid19.plasma.model.RequestStatus;
import com.covid19.plasma.dao.PlasmaDonorRepoisitory;
import com.covid19.plasma.exception.PlasmaDonorNotFound;
import com.covid19.plasma.model.PlasmaDonor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlasmaService {

    @Autowired
    PlasmaDonorRepoisitory repository;
    
    public List<PlasmaDonor> getAllPlasmaDonors() {
        return (List<PlasmaDonor>) repository.findAll();
    }

    public PlasmaDonor getPlasmaDonor(long id) throws PlasmaDonorNotFound {
        Optional<PlasmaDonor> optionalDog = repository.findById(id);
        return optionalDog.orElseThrow(() -> new PlasmaDonorNotFound("Couldn't find a Plasma Donor with id: " + id));
    }

    public void registerPlasmaDonor(PlasmaDonor plasmaDonor) {
        plasmaDonor.setRequestStatus(String.valueOf(RequestStatus.ACTIVE));
        repository.save(plasmaDonor);
    }
}
