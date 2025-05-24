package com.hps.userservice.services.implementations;

import com.hps.userservice.entities.Director;
import com.hps.userservice.repositories.DirectorRepository;
import com.hps.userservice.services.interfaces.DirectorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorServiceImp implements DirectorService {
    private final DirectorRepository directorRepository;

    public DirectorServiceImp(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    @Override
    public Director getDirectorById(Long id) {
        return directorRepository.findById(id).orElse(null);
    }

    @Override
    public Director createDirector(Director director) {
        return directorRepository.save(director);
    }

    @Override
    public Director updateDirector(Long id, Director director) {
        return directorRepository.save(director);
    }

    @Override
    public void deleteDirectorById(Long id) {
        directorRepository.deleteById(id);
    }
}
