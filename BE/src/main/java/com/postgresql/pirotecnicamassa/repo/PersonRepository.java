package com.postgresql.pirotecnicamassa.repo;

import com.postgresql.pirotecnicamassa.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);
    List<Person> findByRole(String role);

}