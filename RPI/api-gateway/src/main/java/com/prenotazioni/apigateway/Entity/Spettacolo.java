package com.prenotazioni.apigateway.Entity;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

public class Spettacolo {
    
    @Getter @Setter
    private String spettacolo;

    @Getter @Setter @NotEmpty
    private List<Sala> sala;

    @Getter @Setter
    private int costo;

    public Spettacolo(){}

    public Spettacolo(String spettacolo, List<Sala> sala, int costo){
        this.spettacolo = spettacolo;
        this.sala = sala;
        this.costo = costo;
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