package com.covid19.plasma.util;

import com.covid19.plasma.dao.entities.PlasmaDonor;
import com.covid19.plasma.dao.entities.PlasmaRequestor;
import com.covid19.plasma.dao.entities.RequestorDonorMapperEntity;
import com.covid19.plasma.enums.RequestToDonorStatus;
import com.covid19.plasma.enums.UserType;
import com.covid19.plasma.exception.PlasmaDonorNotFound;
import com.covid19.plasma.exception.PlasmaRequestorNotFound;
import com.covid19.plasma.exception.UnknownException;
import com.covid19.plasma.model.PlasmaDonorDto;
import com.covid19.plasma.model.PlasmaRequestDto;
import com.covid19.plasma.model.ReceivedPlasmaRequest;
import com.covid19.plasma.model.RequestorDonorMapperDto;
import com.covid19.plasma.security.facade.IAuthenticationFacade;
import com.covid19.plasma.security.model.AppUser;
import com.covid19.plasma.service.PlasmaDonorService;
import com.covid19.plasma.service.PlasmaRequestService;
import com.covid19.plasma.service.RequestorDonorMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class EntityDtoMapper {

    @Autowired
    IAuthenticationFacade authenticationFacade;
    
    @Autowired
    PlasmaRequestService plasmaRequestService;
    
    @Autowired
    PlasmaDonorService plasmaDonorService;
    
    @Autowired
    RequestorDonorMapperService requestorDonorMapperService;
    
    @Autowired
    private ModelMapper modelMapper;

    public PlasmaDonor convertToEntity(PlasmaDonorDto plasmaDonorDto) {
        PlasmaDonor plasmaDonor = modelMapper.map(plasmaDonorDto, PlasmaDonor.class);
        plasmaDonor.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        plasmaDonor.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        plasmaDonor.setAge(PlasmaUtil.getAge(plasmaDonorDto.getDob()));
        return plasmaDonor;
    }

    public PlasmaDonorDto convertToDto(PlasmaDonor plasmaDonor, List<RequestorDonorMapperEntity> requestedDonors) {
        PlasmaDonorDto plasmaDonorDto = modelMapper.map(plasmaDonor, PlasmaDonorDto.class);

        plasmaDonorDto.setId(GlobalId.encode(plasmaDonor.getId()));
        plasmaDonorDto.setRegisteredOn(plasmaDonor.getCreatedAt());
        plasmaDonorDto.setPhoneNumber(PlasmaUtil.maskLeavingLast4(plasmaDonor.getPhoneNumber()));

        populateReceivedPlasmaRequest(plasmaDonor, requestedDonors, plasmaDonorDto);
        return plasmaDonorDto;
    }

    public PlasmaRequestor plasmaRequestDtoToEntity(PlasmaRequestDto plasmaRequestDto) {
        PlasmaRequestor plasmaRequestor = modelMapper.map(plasmaRequestDto, PlasmaRequestor.class);
        plasmaRequestor.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        plasmaRequestor.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        plasmaRequestor.setAge(PlasmaUtil.getAge(plasmaRequestDto.getDob()));
        return plasmaRequestor;
    }

    public PlasmaRequestDto plasmaRequestorToDto(PlasmaRequestor plasmaRequestor) {
        PlasmaRequestDto plasmaRequestDto = modelMapper.map(plasmaRequestor, PlasmaRequestDto.class);
        plasmaRequestDto.setId(GlobalId.encode(plasmaRequestor.getId()));
        plasmaRequestDto.setRequestedOn(plasmaRequestor.getCreatedAt());

        //Masking Number for Plasma Requestors
        if (!(authenticationFacade.isAuthenticated() && UserType.PLASMA_DONOR.equals(getPrincipal().getUserType()))) {
            plasmaRequestDto.setPhoneNumber(PlasmaUtil.maskLeavingLast4(plasmaRequestor.getPhoneNumber()));
        }
        return plasmaRequestDto;
    }

    public RequestorDonorMapperEntity requestorRequestDonorToEntity(RequestorDonorMapperDto requestorDonorMapperDto) {

        if (isNull(requestorDonorMapperDto.getPlasmaDonorId())) {
            throw new UnknownException("plasma donorId is required");
        }

        requestorDonorMapperDto.setPlasmaDonorId(String.valueOf(GlobalId.decode(requestorDonorMapperDto.getPlasmaDonorId())));

        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        RequestorDonorMapperEntity requestorRequestDonor = modelMapper.map(requestorDonorMapperDto, RequestorDonorMapperEntity.class);

        Long plasmaDonorId = Long.parseLong(requestorDonorMapperDto.getPlasmaDonorId());
        Optional<PlasmaDonor> plasmaDonorOptional = plasmaDonorService.findById(plasmaDonorId);

        plasmaDonorOptional.ifPresentOrElse(
                (PlasmaDonor) -> {
                    requestorRequestDonor.setPlasmaDonor(PlasmaDonor);
                },
                () -> {
                    throw new PlasmaDonorNotFound("plasma donor does not exist");
                });

        if (isNull(requestorDonorMapperDto.getPlasmaRequestorId())) {
            AppUser user = authenticationFacade.getPrincipal();
            PlasmaRequestor plasmaRequestor = plasmaRequestService.findByPhoneNumber(user.getUsername());

            if (isNull(plasmaRequestor)) {
                throw new PlasmaRequestorNotFound("plasma requestor not found");
            }

            requestorRequestDonor.setPlasmaRequestor(plasmaRequestor);
        }

        requestorRequestDonor.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        requestorRequestDonor.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        requestorRequestDonor.setStatus(RequestToDonorStatus.PENDING);
        return requestorRequestDonor;
    }

    private void populateReceivedPlasmaRequest(PlasmaDonor plasmaDonor, List<RequestorDonorMapperEntity> requestedDonors, PlasmaDonorDto plasmaDonorDto) {
        if (nonNull(requestedDonors) && requestedDonors.size() > 0) {
            RequestorDonorMapperEntity alreadyRequestedDonor = requestedDonors.stream()
                    .filter(requestedDonor -> plasmaDonor.getId().equals(requestedDonor.getPlasmaDonor().getId()))
                    .findAny()
                    .orElse(null);
            if (nonNull(alreadyRequestedDonor)) {
                ReceivedPlasmaRequest receivedPlasmaRequest = new ReceivedPlasmaRequest();
                receivedPlasmaRequest.setPresent(true);
                plasmaDonorDto.setRequestorRequestPlasma(receivedPlasmaRequest);
            }
        }
    }

    private AppUser getPrincipal() {
        return authenticationFacade.getPrincipal();
    }
}
