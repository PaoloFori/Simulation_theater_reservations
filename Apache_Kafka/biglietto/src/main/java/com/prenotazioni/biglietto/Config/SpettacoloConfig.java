package com.prenotazioni.biglietto.Config;

import java.util.ArrayList;
import java.util.List;

/*import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;*/

import lombok.Getter;
import lombok.Setter;

//@Entity(name = "SPETTACOLO")
public class SpettacoloConfig {
    
    //@Id 
    @Getter @Setter 
    //@Column(name = "SPETTACOLO_ID")
    private String spettacolo;

    @Getter @Setter 
    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) 
    //@Column(name = "SALA")
    private List<SalaConfig> sala = new ArrayList<>();

    @Getter @Setter 
    //@Column(name ="COSTO")
    private int costo;

    @Getter @Setter
    private String action;

    public SpettacoloConfig(){
        sala = new ArrayList<>();
    }

    public SpettacoloConfig(String spettacolo, int costo, List<SalaConfig> sala, String action){
        this.spettacolo = spettacolo;
        this.sala = sala;
        this.costo = costo;
        this.action = action;
    }

    @Override
    public String toString(){
        String s = "{\"spettacolo\":\"" + spettacolo + "\",\n\"costo\":" + costo + ",\n\"sala\":[";
        for(int i = 0; i < sala.size(); i++){
            SalaConfig sala1 = sala.get(i);
            if(i == sala.size()-1){
                s = s + sala1.toString();
            }else{
                s = s + sala1.toString() + ",";
            }
        }
        s = s + "]}";
        return s;
    }
}