package com.postgresql.pirotecnicamassa.service;

import com.postgresql.pirotecnicamassa.model.CategoriaOrdine;
import com.postgresql.pirotecnicamassa.repo.CategoriaOrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.util.List;

// SERVICE
@Service
public class CategoriaOrdineService {
    @Autowired
    private CategoriaOrdineRepository repository;

    public void salvaOrdine(List<String> categorie) {
        repository.deleteAll(); // Cancella il vecchio ordine
        for (int i = 0; i < categorie.size(); i++) {
            repository.save(new CategoriaOrdine(categorie.get(i), i));
        }
    }
    public List<CategoriaOrdine> getOrdine() {
        return repository.findAll();
    }
}
