package com.hps.userservice.controllers;

import com.hps.userservice.entities.Developer;
import com.hps.userservice.services.interfaces.DevelopperService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@RequestMapping("/developpers")
public class DevelopperController {
    private final DevelopperService developperService;

    public DevelopperController(DevelopperService developperService) {
        this.developperService = developperService;
    }
    @GetMapping
    public List<Developer> getAllDeveloppers(){
        return developperService.getAllDeveloppers();
    }

    @GetMapping("/{id}")
    public Developer getDevelopperById(@PathVariable Long id){
        return developperService.getDevelopperById(id);
    }

    @PostMapping()
    public Developer createDevelopper(@RequestBody Developer developer){
        return developperService.createDeveloper(developer);
    }

    @DeleteMapping("/{id}")
    public void deleteDevelopperById(@PathVariable Long id){
        developperService.deleteDevelopperById(id);
    }

    @PutMapping("/{id}")
    public Developer updateDevelopper(@PathVariable Long id, @RequestBody Developer developper){
        return developperService.updateDevelpper(id, developper);
    }

}
