package com.postgresql.pirotecnicamassa.repo;

import com.postgresql.pirotecnicamassa.model.CategoriaOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// REPOSITORY
@Repository
public interface CategoriaOrdineRepository extends JpaRepository<CategoriaOrdine, Long> {
    void deleteAll();
}