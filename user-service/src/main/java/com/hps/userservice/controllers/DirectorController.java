package com.hps.userservice.controllers;


import com.hps.userservice.entities.Director;
import com.hps.userservice.services.interfaces.DevelopperService;
import com.hps.userservice.services.interfaces.DirectorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@RequestMapping("/directors")
public class DirectorController {
    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public List<Director> getAllDirectors(){
        return directorService.getAllDirectors();
    }

    @GetMapping("/{id}")
    public Director getDirectorById(@PathVariable Long id){
        return directorService.getDirectorById(id);
    }

    @PostMapping()
    public Director createDirector(@RequestBody Director director){
        return directorService.createDirector(director);
    }

    @DeleteMapping("/{id}")
    public void deleteDirectorById(@PathVariable Long id){
        directorService.deleteDirectorById(id);
    }

    @PutMapping("/{id}")
    public Director updateDirector(@PathVariable Long id, @RequestBody Director director){
        return directorService.updateDirector(id, director);
    }

}
