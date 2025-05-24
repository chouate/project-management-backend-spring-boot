package com.hps.userservice.services.interfaces;

import com.hps.userservice.entities.Director;

import java.util.List;

public interface DirectorService {
    List<Director> getAllDirectors();
    Director getDirectorById(Long id);
    Director createDirector(Director director);
    Director updateDirector(Long id, Director director);
    void deleteDirectorById(Long id);

}
