package com.postgresql.pirotecnicamassa.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "name")
    private String name;

    @Column(name = "password") // **IMPORTANTE:** Hash la password!
    private String password;

    @Column(name = "role", columnDefinition = "VARCHAR(255) DEFAULT 'user'") // Ruolo definito nel DB, default 'user'
    private String role;

    public Person(String email, boolean enabled, String name, String password) {
        this.email = email;
        this.enabled = enabled;
        this.name = name;
        this.password = password;
    }


}
