package com.covid19.plasma.service;

import com.covid19.plasma.dao.PlasmaRequestorRepoisitory;
import com.covid19.plasma.dao.entities.PlasmaRequestor;
import com.covid19.plasma.dao.entities.RequestorDonorMapperEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestorDonorMapperService {

    @Autowired
    PlasmaRequestorRepoisitory plasmaRequestorRepoisitory;

    public void requestorRequestDonor(RequestorDonorMapperEntity requestorDonorMapperEntity) {
        
        List<RequestorDonorMapperEntity> requestorDonorMapperEntityList = new ArrayList<>();
        requestorDonorMapperEntityList.add(requestorDonorMapperEntity);

        PlasmaRequestor plasmaRequestor = requestorDonorMapperEntity.getPlasmaRequestor();
        plasmaRequestor.setRequestedDonors(requestorDonorMapperEntityList);

        plasmaRequestorRepoisitory.save(plasmaRequestor);
    }
}
