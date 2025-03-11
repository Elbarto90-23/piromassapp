package com.postgresql.pirotecnicamassa.controller;

import com.postgresql.pirotecnicamassa.model.Prodotto;
import com.postgresql.pirotecnicamassa.repo.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost")
public class ProdottoController {

    @Autowired
    private ProdottoRepository prodottoRepository;

    private final String UPLOAD_DIR = "src/main/resources/static/prodotti/";

    // ✅ Endpoint per ottenere un prodotto per ID
    @GetMapping("/prodotti/{id}")
    public ResponseEntity<Prodotto> getProdottoById(@PathVariable Long id) {
        Optional<Prodotto> prodotto = prodottoRepository.findById(id);
        return prodotto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Endpoint per aggiungere un prodotto
    @PostMapping("/carica")
    public ResponseEntity<String> caricaProdotto(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("nome") String nome,
                                                 @RequestParam("descrizione") String descrizione,
                                                 @RequestParam("prezzo") Double prezzo,
                                                 @RequestParam("categoria") String categoria) throws IOException {
        // Salvataggio dell'immagine nella cartella 'prodotti'
        String fileName = file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(file.getInputStream(), path);

        // Creazione del prodotto
        Prodotto prodotto = new Prodotto();
        prodotto.setNome(nome);
        prodotto.setDescrizione(descrizione);
        prodotto.setPrezzo(prezzo);
        prodotto.setImmagineUrl("/prodotti/" + fileName); // Salvataggio dell'URL relativo
        prodotto.setCategoria(categoria);
        prodotto.setDisponibilita(true);

        // Salvataggio del prodotto nel database
        prodottoRepository.save(prodotto);

        return ResponseEntity.ok("Prodotto caricato con successo!");
    }

    // ✅ Endpoint per recuperare tutti i prodotti
    @GetMapping("/prodotti")
    public ResponseEntity<List<Prodotto>> getProdotti() {
        List<Prodotto> prodotti = prodottoRepository.findAll();
        return ResponseEntity.ok(prodotti);
    }

    // ✅ Ottieni tutte le categorie uniche
    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return prodottoRepository.findAllCategories();
    }

    // ✅ Filtra prodotti per categoria
    @GetMapping("/filter")
    public ResponseEntity<List<Prodotto>> getProductsByCategory(@RequestParam String categoria) {
        List<Prodotto> prodotti = prodottoRepository.findByCategoria(categoria);
        return ResponseEntity.ok(prodotti);
    }
}
