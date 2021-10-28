package com.prenotazioni.biglietto.Config;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "User")
public class UserConfig{

    @Id
    private int id;

    @Getter @Setter 
    private String name;

    @Getter @Setter 
    private String surname;

    @Getter @Setter
    private int cash;

    @Getter @Setter
    private String action;

    public UserConfig(int id, String name, String surname, int cash, String action){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.cash = cash;
        this.action = action;
    }

    public UserConfig(){
        
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
        return "{\"id\":"+ id+",\n\"name\":\""+ name+ "\",\n\"surname\":\"" + surname + "\",\n\"cash\":" + cash + "\"action\":\""+ action +"\"}";
    }
}