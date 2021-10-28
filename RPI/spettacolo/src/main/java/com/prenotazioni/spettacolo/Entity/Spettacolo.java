package com.prenotazioni.spettacolo.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "spettacolo")
public class Spettacolo {
    
    @Id @Getter @Setter
    private String spettacolo;

    @Getter @Setter @NotEmpty
    private List<Sala> sala;

    @Getter @Setter
    private int costo;

    public Spettacolo(){
    	sala = new ArrayList<>();
    }

    public Spettacolo(String spettacolo, List<Sala> sala, int costo){
        this.spettacolo = spettacolo;
        this.sala = sala;
        this.costo = costo;
    }
    
    public Spettacolo(String spettacolo, int costo) {
    	this.spettacolo = spettacolo;
    	this.costo = costo;
    	sala = new ArrayList<>();
    }

    public void addSala(Sala sal){
        sala.add(sal);
    }

    public String toString(){
        String s = "{spettacolo: " + spettacolo + ",\ncosto: " + costo + " \nsala: [";
        for(int i = 0; i < sala.size(); i++){
            Sala sala1 = sala.get(i);
            if(i == sala.size()-1){
                s = s + "   " +sala1.toString();
            }else{
                s = s + "   " +sala1.toString() + ", ";
            }
        }
        s = s + "]}";
        return s;
    }
}