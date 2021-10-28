package com.prenotazioni.apigateway.Entity;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

public class User {

    private int id;

    @Getter @Setter @Size(min = 1, max = 32)
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