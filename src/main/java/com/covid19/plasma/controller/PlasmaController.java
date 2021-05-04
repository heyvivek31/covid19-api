package com.covid19.plasma.controller;

import com.covid19.plasma.exception.PlasmaDonorNotFound;
import com.covid19.plasma.model.PlasmaDonor;
import com.covid19.plasma.service.PlasmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlasmaController {

    @Autowired
    PlasmaService plasmaService;

    @RequestMapping(method = RequestMethod.POST, value = "/plasma/donors")
    public void registerPlasmaDonor(@RequestBody PlasmaDonor plasmaDonor) {
        plasmaService.registerPlasmaDonor(plasmaDonor);
    }
    
    @RequestMapping("/plasma/donors")
    public List<PlasmaDonor> getAllPlasmaDonors() {
        return plasmaService.getAllPlasmaDonors();
    }

    @RequestMapping("/plasma/donors/{id}")
    public PlasmaDonor getPlasmaDonor(@PathVariable long id) throws PlasmaDonorNotFound {
        return plasmaService.getPlasmaDonor(id);
    }
}
