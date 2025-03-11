package com.postgresql.pirotecnicamassa.service;

import com.postgresql.pirotecnicamassa.model.Person;
import com.postgresql.pirotecnicamassa.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public abstract class PersonService {

    @Autowired
    private PersonRepository personRepository;

    // Metodo per registrare un nuovo utente
    public Optional<Person> registerUser(Person person) {
        // Controllo se l'email esiste già
        if (personRepository.findByEmail(person.getEmail()).isPresent()) {
            return Optional.empty();  // Restituiamo un Optional vuoto per indicare che l'utente esiste già
        }
        // Se l'email non esiste, registriamo l'utente
        return Optional.of(personRepository.save(person));
    }

    // Metodo per autenticare l'utente
    public Optional<Person> authenticateUser(String email, String password) {
        return personRepository.findByEmail(email)
                .filter(person -> person.getPassword().equals(password));
    }

    public abstract UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}


