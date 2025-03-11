package com.postgresql.pirotecnicamassa.Dto;

public class PersonDto {

    private String name;
    private String email;
    private String password;

    public PersonDto() {
    }

    public PersonDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and setters omitted for brevity
}