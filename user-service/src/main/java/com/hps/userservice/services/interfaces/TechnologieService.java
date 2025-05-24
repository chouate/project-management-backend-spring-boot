package com.hps.userservice.services.interfaces;

import com.hps.userservice.entities.Technology;

import java.util.List;


public interface TechnologieService {
    List<Technology> getAllTechnologies();
    Technology getTechnologieById(Integer id);
    Technology createTechnologie(Technology technology);
    Technology updateTechnologie(Integer id, Technology technology);
    void deleteTechnologie(Integer id);
}
