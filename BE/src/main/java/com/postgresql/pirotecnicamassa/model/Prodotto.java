package com.postgresql.pirotecnicamassa.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "prodotto")
@Data
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "disponibilita")
    private boolean disponibilita;

    @Column(name = "immagine_url")
    private String immagineUrl;

    @Column(name = "nome")
    private String nome;

    @Column(name = "position")
    private Integer position;

    @Column(name = "prezzo")
    private Double prezzo;

    @Transient // Non viene salvato nel database, serve solo per l'ordine
    private int quantita;

    // Costruttore vuoto richiesto da JPA
    public Prodotto() {
    }

    // Costruttore con parametri
    public Prodotto(String categoria, String descrizione, boolean disponibilita, String immagineUrl, String nome, Integer position, Double prezzo, int quantita) {
        this.categoria = categoria;
        this.descrizione = descrizione;
        this.disponibilita = disponibilita;
        this.immagineUrl = immagineUrl;
        this.nome = nome;
        this.position = position;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }

    // Getter e Setter per quantita
    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}
