package com.prenotazioni.spettacolo.Entity;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

public class Sala {

    @Id @Getter @Setter
    private int numeroSala;

    @Getter @Setter @NotEmpty
    private List<Posto> posto;

    public Sala(){}

    public Sala(int numeroSala, List<Posto> posto){
        this.numeroSala = numeroSala;
        this.posto = posto;
    }

    public Sala(int numeroSala){
        this.numeroSala = numeroSala;
    }

    public String toString(){
        String s = "{\"numeroSala\":"+numeroSala+",\"posto\":[";
        for(int i = 0; i < posto.size(); i++){
            Posto posto1 = posto.get(i);
            if(i == posto.size()-1){
                s = s + posto1.toString();
            }else{
                s = s + posto1.toString() + ", ";
            }
        }
        s = s + "]}";
        return s;
    }
}