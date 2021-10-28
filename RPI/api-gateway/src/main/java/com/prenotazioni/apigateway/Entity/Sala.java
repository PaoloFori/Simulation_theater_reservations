package com.prenotazioni.apigateway.Entity;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

public class Sala {

    @Getter @Setter
    private int numeroSala;

    @Getter @Setter @NotEmpty
    private List<Posto> posto;

    public Sala(){}

    public Sala(int numeroSala, List<Posto> posto){
        this.numeroSala = numeroSala;
        this.posto = posto;
    }

    public String toString(){
        String s = "\n   {numeroSala: "+numeroSala+", posti: [\n";
        for(int i = 0; i < posto.size(); i++){
            Posto posto1 = posto.get(i);
            if(i == posto.size()-1){
                s = s + "            " +posto1.toString();
            }else{
                s = s + "            " + posto1.toString() + ",\n";
            }
        }
        s = s + "]}";
        return s;
    }
}