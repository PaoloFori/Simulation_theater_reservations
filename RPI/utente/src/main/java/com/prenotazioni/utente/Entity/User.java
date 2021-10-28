package com.prenotazioni.utente.Entity;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "utente")
public class User {

    @Id @Setter
    private int id;

    @Getter @Setter
    private String name;

    @Getter @Setter @Size(min = 1, max = 32)
    private String surname;

    @Getter @Setter
    private int cash;

    public User(int id, String name, String surname, int cash){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.cash = cash;
    }

    public User(){}

    //getter id
    public int getId(){
        return id;
    }

    public String toString(){
        return "{\n id: "+ id +"\n name: " + name +"\n surname: "+surname +"\n saldo: " + cash + "\n}";
    }
}