package com.hps.clientservice.repositories;

import com.hps.clientservice.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByCode(String code);

    List<Client> findClientByDirectorId(Long directorId);
}
