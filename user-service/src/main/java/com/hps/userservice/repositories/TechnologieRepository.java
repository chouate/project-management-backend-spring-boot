package com.hps.userservice.repositories;

import com.hps.userservice.entities.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechnologieRepository extends JpaRepository<Technology, Integer> {
    public Optional<Technology> findTechnologieByName(String technologieName);

    @Query("SELECT t FROM Technology t WHERE t.id IN :ids")
    List<Technology> findAllByIdIn(@Param("ids") List<Long> ids);

}
