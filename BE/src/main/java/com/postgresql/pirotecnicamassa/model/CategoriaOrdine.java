package com.postgresql.pirotecnicamassa.model;

import jakarta.persistence.*;

// ENTITY
@Entity
@Table(name = "categorie_ordine")
public class CategoriaOrdine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private int posizione;

    // Costruttori, getter e setter
    public CategoriaOrdine() {}
    public CategoriaOrdine(String nome, int posizione) {
        this.nome = nome;
        this.posizione = posizione;
    }
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public int getPosizione() { return posizione; }
    public void setNome(String nome) { this.nome = nome; }
    public void setPosizione(int posizione) { this.posizione = posizione; }
}
