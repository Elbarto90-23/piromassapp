package com.postgresql.pirotecnicamassa.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomPersonDetails implements UserDetails {

    private final Person person;

    public CustomPersonDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + person.getRole())); // Assegna il ruolo
    }

    @Override
    public String getPassword() {
        return person.getPassword(); // Restituisce la password
    }

    @Override
    public String getUsername() {
        return person.getEmail(); // Usa l'email come username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return person.isEnabled(); // Controlla se l'account è abilitato
    }
}
