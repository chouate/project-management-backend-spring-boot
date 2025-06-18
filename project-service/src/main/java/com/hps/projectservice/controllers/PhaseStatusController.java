package com.hps.projectservice.controllers;

import com.hps.projectservice.entities.StatusPhase;
import com.hps.projectservice.services.interfaces.StatusService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statuses")
public class PhaseStatusController {
    private final StatusService statusService;

    public PhaseStatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    public List<StatusPhase> getAllStatuses() {
        return statusService.getAllStatuses();
    }

    @GetMapping("/{id}")
    public StatusPhase getStatus(@PathVariable Long id) {
        return statusService.getStatusById(id);
    }

    @PostMapping
    public StatusPhase createStatus(@RequestBody StatusPhase status) {
        return statusService.createNewStatus(status);
    }

    @PutMapping("/{id}")
    public StatusPhase updateStatus(@PathVariable Long id, @RequestBody StatusPhase status) {
        return statusService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteStatus(@PathVariable Long id) {
        statusService.deleteStatusById(id);
    }
}
