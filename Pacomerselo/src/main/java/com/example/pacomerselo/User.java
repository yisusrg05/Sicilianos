package com.example.pacomerselo;

import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
public class User {

    private String username;
    private String name;
    private String surname;
    private String password;
    private Boolean admin;

    public User(String username,String name, String surname, String password){
        this.username=username;
        this.name=name;
        this.surname=surname;
        this.password=password;
        this.admin=false;
    }
}
