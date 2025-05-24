package com.hps.userservice.services.implementations;

import com.hps.userservice.entities.Technology;
import com.hps.userservice.exceptions.ResourceAlreadyExistException;
import com.hps.userservice.exceptions.ResourceNotFoundException;
import com.hps.userservice.repositories.TechnologieRepository;
import com.hps.userservice.services.interfaces.TechnologieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnologieServiceImp implements TechnologieService {
    private final TechnologieRepository technologieRepository;

    public TechnologieServiceImp(TechnologieRepository technologieRepository) {
        this.technologieRepository = technologieRepository;
    }

    @Override
    public List<Technology> getAllTechnologies() {
        return technologieRepository.findAll();
    }

    @Override
    public Technology getTechnologieById(Integer id) {

        return this.technologieRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Technology,to get, not found with id: "+id));
    }

    @Override
    public Technology createTechnologie(Technology technology) {
        Optional<Technology> technologieExisting = this.technologieRepository.findTechnologieByName(technology.getName());

        if(technologieExisting.isPresent()){
            throw new ResourceAlreadyExistException("Technology already exist : "+ technology.getName());
        }

        return technologieRepository.save(technology);
    }

    @Override
    public Technology updateTechnologie(Integer id, Technology technology) {
        //chek if the technology, to update, exist in database
        Technology technologyExisting = this.technologieRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Technology, to update, not found with id: "+id));

        //Check if the new technology name exist in other technology in database
        Optional<Technology> optionalTechnologie =  this.technologieRepository.findTechnologieByName(technology.getName());
        if(optionalTechnologie.isPresent() && (optionalTechnologie.get().getId() != id) ){
            throw new ResourceAlreadyExistException("Technology already exist with name: "+ technology.getName()+", try to update with other name.");
        }

        //else save the modification
        technology.setId(id);
        return technologieRepository.save(technology);
    }

    @Override
    public void deleteTechnologie(Integer id) {
        Technology technologyExisting = this.technologieRepository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("Technology, to delete, not found with id: "+id));
        technologieRepository.deleteById(id);
    }
}
