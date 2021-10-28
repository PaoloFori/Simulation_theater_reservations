package com.prenotazioni.biglietto.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Spettacolo")
public class Spettacolo {
    
    @Id 
    @Getter @Setter 
    @Column(name = "SPETTACOLO_ID")
    private String spettacolo;
 
    @OneToMany(mappedBy = "show")
    @Getter @Setter
    private List<Sala> sala = new ArrayList<>();

    @Getter @Setter 
    @Column(name ="COSTO")
    private int costo;

    public Spettacolo(){
    }

    public Spettacolo(String spettacolo, int costo, List<Sala> sala){
        this.spettacolo = spettacolo;
        this.sala = sala;
        this.costo = costo;
    }

    public Spettacolo(String spettacolo, int costo){
        this.spettacolo = spettacolo;
        this.costo = costo;
        sala = new ArrayList<>();
    }

    @Override
    public String toString(){
        String s = "{\"spettacolo\":\"" + spettacolo + "\",\n\"costo\":" + costo + ",\n\"sala\":[";
        for(int i = 0; i < sala.size(); i++){
            Sala sala1 = sala.get(i);
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