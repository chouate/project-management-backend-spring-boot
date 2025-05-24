package com.hps.projectservice.controllers;

import com.hps.projectservice.entities.Phase;
import com.hps.projectservice.repositories.PhaseRepository;
import com.hps.projectservice.services.interfaces.PhaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phases")
public class PhaseController {
    private final PhaseService phaseService;

    public PhaseController(PhaseService phaseService) {
        this.phaseService = phaseService;
    }


    @GetMapping
    public List<Phase> getAllPhases() {
        return phaseService.getAllPhases();
    }

    @GetMapping("/{id}")
    public Phase getPhase(@PathVariable int id) {
        return phaseService.getPhaseById(id);
    }

    @PostMapping
    public Phase createPhase(@RequestBody Phase phase) {
        return phaseService.createNewPhase(phase);
    }

    @PutMapping("/{id}")
    public Phase updatePhase(@PathVariable int id, @RequestBody Phase phase) {
        return phaseService.updatePhase(id, phase);
    }

    @DeleteMapping("/{id}")
    public void deletePhase(@PathVariable int id) {
        phaseService.deletePhaseById(id);
    }

}
