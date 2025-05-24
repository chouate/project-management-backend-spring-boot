package com.hps.userservice.repositories;

import com.hps.userservice.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);

//    @EntityGraph(attributePaths = {
//            "listProjectManagersAffected",
//            "listDevelopersAffected"
//    })
    Optional<User> findByKeycloakId(String keycloakId);
    @Query("""
    SELECT d 
      FROM Director d
      LEFT JOIN FETCH d.listDevelopersAffected
      LEFT JOIN FETCH d.ListProjectManagersAffected
      WHERE d.keycloakId = :kcId
    """)
    Optional<User> findByKeycloakIdWithTeams(@Param("kcId") String keycloakId);
}
