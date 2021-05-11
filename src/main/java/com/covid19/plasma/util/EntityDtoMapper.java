package com.covid19.plasma.util;

import com.covid19.plasma.dao.entities.PlasmaDonor;
import com.covid19.plasma.dao.entities.PlasmaRequestor;
import com.covid19.plasma.model.PlasmaDonorDto;
import com.covid19.plasma.model.PlasmaRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class EntityDtoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PlasmaDonor convertToEntity(PlasmaDonorDto plasmaDonorDto) {
        PlasmaDonor plasmaDonor = modelMapper.map(plasmaDonorDto, PlasmaDonor.class);
        plasmaDonor.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        plasmaDonor.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return plasmaDonor;
    }

    public PlasmaDonorDto convertToDto(PlasmaDonor plasmaDonor) {
        PlasmaDonorDto plasmaDonorDto = modelMapper.map(plasmaDonor, PlasmaDonorDto.class);
        plasmaDonorDto.setId(GlobalId.encode(plasmaDonor.getId()));
        plasmaDonorDto.setRegisteredOn(plasmaDonor.getCreatedAt());
        plasmaDonorDto.setPhoneNumber(PlasmaUtil.maskLeavingLast4(plasmaDonor.getPhoneNumber()));
        return plasmaDonorDto;
    }

    public PlasmaRequestor plasmaRequestDtoToEntity(PlasmaRequestDto plasmaRequestDto) {
        PlasmaRequestor plasmaRequestor = modelMapper.map(plasmaRequestDto, PlasmaRequestor.class);
        plasmaRequestor.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        plasmaRequestor.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return plasmaRequestor;
    }

    public PlasmaRequestDto plasmaRequestorToDto(PlasmaRequestor plasmaRequestor) {
        PlasmaRequestDto plasmaRequestDto = modelMapper.map(plasmaRequestor, PlasmaRequestDto.class);
        plasmaRequestDto.setId(GlobalId.encode(plasmaRequestor.getId()));
        plasmaRequestDto.setRequestedOn(plasmaRequestor.getCreatedAt());
        plasmaRequestDto.setPhoneNumber(PlasmaUtil.maskLeavingLast4(plasmaRequestor.getPhoneNumber()));
        return plasmaRequestDto;
    }
}
