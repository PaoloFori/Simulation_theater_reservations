package com.prenotazioni.spettacolo.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

public class Posto {

    @Getter @Setter @NotNull @Size(min =1, max =1, message = "only one letter") 
    private String riga;

    @Getter @Setter @NotNull @Min(1) @Max(2) 
    private int colonna;

    private boolean free;

    public Posto(String riga, int colonna, boolean free){
        this.riga = riga;
        this.colonna = colonna;
        this.free = free;
    }

    public Posto(){}

    public boolean getFree(){
        return free;
    }

    public void setFree(boolean free){
        this.free = free;
    }

    public String toString(){
        return "      {riga: "+riga+", colonna: "+colonna+", free: "+free+"}";
    }
}