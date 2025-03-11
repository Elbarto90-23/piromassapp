package com.postgresql.pirotecnicamassa.repo;

import com.postgresql.pirotecnicamassa.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface PersonRepo extends JpaRepository<Person, Long> {
    // Metodo per verificare se un'email esiste già nel database
    boolean existsByEmail(String email);}
