package com.hps.userservice.controllers;


import com.hps.userservice.entities.Technology;
import com.hps.userservice.services.interfaces.TechnologieService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("technologies")
@Validated
public class TechnologieController {
    private final TechnologieService technologieService;

    public TechnologieController(TechnologieService technologieService) {
        this.technologieService = technologieService;
    }

    @GetMapping
    public List<Technology> getALLTechnologies(){
        return technologieService.getAllTechnologies();
    }

    @GetMapping("/{id}")
    public Technology getTechnologieById(@PathVariable Integer id){
        return technologieService.getTechnologieById(id);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public Technology createNewTechnologie(@Valid @RequestBody Technology technology){
        return technologieService.createTechnologie(technology);
    }

    @PutMapping("/{id}")
    public Technology updateTechnologie(@PathVariable Integer id, @Valid @RequestBody Technology technology){
        return technologieService.updateTechnologie(id, technology);
    }

    @DeleteMapping("/{id}")
    public void deleteTechnologie(@PathVariable Integer id){
        this.technologieService.deleteTechnologie(id);
    }
}
