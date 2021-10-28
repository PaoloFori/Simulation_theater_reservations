package com.prenotazioni.biglietto.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Posto")
public class Posto {

    @Getter @Setter 
    @NotNull @Size(min =1, max =1, message = "only one letter") 
    @Column(name ="RIGA")
    private String riga;

    @Getter
    @OneToOne
    @JoinColumn(name = "posto_sala")
    private Sala sala;

    @Getter @Setter 
    @NotNull @Min(1) @Max(2) 
    @Column(name ="COLONNA")
    private int colonna;

    @Column(name ="FREE")
    private boolean free;

    @Getter @Id
    @Column(name = "POSTO_ID")
    private String posto;

    public Posto(String riga, int colonna, boolean free, Sala sala){
        this.riga = riga;
        this.colonna = colonna;
        this.free = free;
        this.sala = sala;
        posto = this.sala.getId() + " posto: " + riga.toUpperCase() + colonna;
    }

    public Posto(String riga, int colonna, boolean free){
        this.riga = riga;
        this.colonna = colonna;
        this.free = free;
        posto = this.sala.getId() + " posto: " + riga.toUpperCase() + colonna;
    }

    public Posto(){}

    public void setSala(Sala sala){
        this.sala = sala;
        posto =  this.sala.getId() + " posto: " + riga.toUpperCase() + colonna;
    }

    public boolean getFree(){
        return free;
    }

    public void setFree(boolean free){
        this.free = free;
    }

    public String postoString(){
        return riga.toUpperCase() + colonna;
    }

    @Override
    public String toString(){
        return "{\"riga\":\""+riga+"\",\n\"colonna\":"+colonna+",\n\"free\": "+free+"}";
    }
}