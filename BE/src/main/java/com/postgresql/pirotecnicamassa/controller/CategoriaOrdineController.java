package com.postgresql.pirotecnicamassa.controller;

import com.postgresql.pirotecnicamassa.model.CategoriaOrdine;
import com.postgresql.pirotecnicamassa.service.CategoriaOrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ordini")
public class CategoriaOrdineController {
    @Autowired
    private CategoriaOrdineService service;

    @PostMapping("/saveOrder")
    public String salvaOrdine(@RequestBody List<String> ordineCategorie) {
        service.salvaOrdine(ordineCategorie);
        return "Ordine salvato con successo";
    }

    @GetMapping("/getOrder")
    public List<String> getOrdine() {
        List<CategoriaOrdine> categorie = service.getOrdine();
        return categorie.stream().map(CategoriaOrdine::getNome).collect(Collectors.toList()); // Restituisce solo i nomi
    }
}
