package com.postgresql.pirotecnicamassa.controller;

import com.postgresql.pirotecnicamassa.model.Person;
import com.postgresql.pirotecnicamassa.repo.PersonRepo;
import com.postgresql.pirotecnicamassa.repo.PersonRepository;
import com.postgresql.pirotecnicamassa.service.CustomPersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CustomPersonDetailsService personService;

    // Endpoint per registrare un nuovo utente
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Person person) {
        Optional<Person> registeredUser = personService.registerUser(person);

        return registeredUser.map(value -> ResponseEntity.ok("Registrazione completata! Benvenuto, " + value.getName())).orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Email già registrata. Usa un'altra email o esegui il cambio password."));

    }

    // Endpoint per il login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(
            @RequestParam String email,
            @RequestParam String password) {

        // Validazione base
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Email e password sono obbligatorie.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Autenticazione
        Optional<Person> authenticatedPerson = personService.authenticateUser(email, password);

        if (authenticatedPerson.isPresent()) {
            Map<String, String> response = new HashMap<>();
            Person user = authenticatedPerson.get();
            response.put("message", "Logato con successo!");
            response.put("name", user.getName());
            response.put("role", user.getRole());
            response.put("email", user.getEmail());

            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Credenziali errate. Riprova.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
