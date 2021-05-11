package com.covid19.plasma.controller;

import com.covid19.plasma.dao.entities.PlasmaDonor;
import com.covid19.plasma.dao.entities.PlasmaRequestor;
import com.covid19.plasma.exception.PlasmaException;
import com.covid19.plasma.model.PlasmaDonorDto;
import com.covid19.plasma.model.PlasmaRequestDto;
import com.covid19.plasma.service.PlasmaDonorService;
import com.covid19.plasma.service.PlasmaRequestService;
import com.covid19.plasma.util.EntityDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PlasmaController {

    @Autowired
    PlasmaDonorService plasmaDonorService;

    @Autowired
    PlasmaRequestService plasmaRequestService;

    @Autowired
    EntityDtoMapper entityDtoMapper;

    @RequestMapping(method = RequestMethod.POST, value = "/plasma/donors")
    public void registerPlasmaDonor(@Valid @RequestBody PlasmaDonorDto plasmaDonorDto) throws ParseException {
        PlasmaDonor plasmaDonor = entityDtoMapper.convertToEntity(plasmaDonorDto);
        plasmaDonorService.registerPlasmaDonor(plasmaDonor);
    }

    @RequestMapping("/plasma/donors")
    public List<PlasmaDonorDto> getAllPlasmaDonors() {
        List<PlasmaDonor> plasmaDonors = plasmaDonorService.getAllPlasmaDonors();
        return plasmaDonors.stream()
                .map(entityDtoMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @RequestMapping("/plasma/donors/{id}")
    public PlasmaDonorDto getPlasmaDonor(@PathVariable long id) throws PlasmaException {
        return entityDtoMapper.convertToDto(plasmaDonorService.getPlasmaDonor(id));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/plasma/requests")
    public void registerPlasmaRequest(@Valid @RequestBody PlasmaRequestDto plasmaRequestDto) throws ParseException {
        PlasmaRequestor plasmaRequestor = entityDtoMapper.plasmaRequestDtoToEntity(plasmaRequestDto);
        plasmaRequestService.registerPlasmaRequest(plasmaRequestor);
    }

    @RequestMapping("/plasma/requests")
    public List<PlasmaRequestDto> getAllPlasmaRequestors() {
        List<PlasmaRequestor> plasmaDonors = plasmaRequestService.getAllPlasmaRequests();
        return plasmaDonors.stream()
                .map(entityDtoMapper::plasmaRequestorToDto)
                .collect(Collectors.toList());
    }

    @RequestMapping("/plasma/requests/{id}")
    public PlasmaRequestDto getPlasmaRequestor(@PathVariable long id) throws PlasmaException {
        return entityDtoMapper.plasmaRequestorToDto(plasmaRequestService.getPlasmaRequestor(id));
    }
}
