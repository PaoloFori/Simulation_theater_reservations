package com.prenotazioni.utente.Entity;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "utente")
public class User {

    @Id
    private int id;

    @Getter @Setter @Size(min = 1, max = 32)
    private String name;

    @Getter @Setter @Size(min = 1, max = 32)
    private String surname;

    @Getter @Setter
    private int cash;

    @Getter @Setter
    private String action;

    public User(int id,String name, String surname, int cash, String action){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.cash = cash;
        this.action = action;
    }

    public User(){}

    //getter id
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString(){
        return "{\"action\":\""+ action+ "\",\"id\":"+ id+",\"name\":\""+ name+ "\",\"surname\":\"" + surname + "\",\"cash\":" + cash + "}";
    }

    
}