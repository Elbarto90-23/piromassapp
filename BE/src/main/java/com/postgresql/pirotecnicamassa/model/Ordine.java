package com.postgresql.pirotecnicamassa.model;

import java.util.List;

public class Ordine {
    private String email;
    private List<Prodotto> prodotti;

    // Getter e Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }
}