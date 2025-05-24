package com.hps.userservice.services.interfaces;

import com.hps.userservice.entities.Developer;


import java.util.List;

public interface DevelopperService {
    List<Developer> getAllDeveloppers();
    Developer getDevelopperById(Long id);
    Developer createDeveloper(Developer developper);
    Developer updateDevelpper(Long id, Developer developper);
    void deleteDevelopperById(Long id);

}
