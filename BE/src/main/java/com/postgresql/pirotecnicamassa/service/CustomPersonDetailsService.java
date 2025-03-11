package com.postgresql.pirotecnicamassa.service;

import com.postgresql.pirotecnicamassa.model.CustomPersonDetails;
import com.postgresql.pirotecnicamassa.repo.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomPersonDetailsService extends PersonService {

    private final PersonRepository personRepository;

    public CustomPersonDetailsService(PersonRepository personRepository) {

        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return personRepository.findByEmail(email)
                .map(CustomPersonDetails::new) // Adatta la tua entità `Person` a `UserDetails`
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con email: " + email));
    }
}
