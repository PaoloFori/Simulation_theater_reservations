package com.prenotazioni.biglietto.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "User")
public class User{

    @Id
    private int id;

    @Getter @Setter 
    private String name;

    @Getter @Setter 
    private String surname;

    @Getter @Setter
    private int cash;

    public User(int id, String name, String surname, int cash){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.cash = cash;
    }

    public User(){
        
    }

    //getter id
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString(){
        return "{\"id\":"+ id+",\n\"name\":\""+ name+ "\",\n\"surname\":\"" + surname + "\",\n\"cash\":" + cash + "}";
    }
}