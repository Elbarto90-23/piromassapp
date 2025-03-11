package com.postgresql.pirotecnicamassa.repo;

import com.postgresql.pirotecnicamassa.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "prodotti", path = "prodotti") // Personalizza l'endpoint REST
public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {

    // Metodo per trovare i prodotti per categoria
    List<Prodotto> findByCategoria(String categoria);

    // Query per ottenere tutte le categorie uniche
    @Query("SELECT DISTINCT p.categoria FROM Prodotto p")
    List<String> findAllCategories();
}
