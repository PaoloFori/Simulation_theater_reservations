package com.prenotazioni.biglietto.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Sala")
public class Sala {

    @Id @Getter
    @Column(name = "SALA_ID")
    private String id;
    
    @Getter @Setter
    @Column(name = "NUMEROSALA")
    private int numeroSala;

    @Getter @Setter
    @OneToMany(mappedBy = "sala")
    private List<Posto> posto = new ArrayList<>();

    @Getter @Setter
    @OneToOne
    @JoinColumn(name = "sala_spettacolo")
    private Spettacolo show;

    public Sala(){
        posto = new ArrayList<>();
    }

    public Sala(int numeroSala, List<Posto> posto, Spettacolo spett){
        this.numeroSala = numeroSala;
        this.posto = posto;
        show = spett;
        id = "Show: " + spett.getSpettacolo() + " sala: " + numeroSala;
    }

    public Sala(int numeroSala, Spettacolo spett){
        show = spett;
        this.numeroSala = numeroSala;
        posto = new ArrayList<>();
        id = "Show: " + spett.getSpettacolo() + " sala: " + numeroSala;
    }

    public Sala(int numeroSala){
        this.numeroSala = numeroSala;
        posto = new ArrayList<>();
        id = "Show: null sala: " + numeroSala;
    }

    @Override
    public String toString(){
        String s = "{\"numeroSala\":"+numeroSala+",\n\"posto\":[";
        for(Posto posto1 : posto){
            if(posto1.equals(posto.get(posto.size()-1)) && (posto.size()>=1)){
                s = s + posto1.toString();
            }else{
                s = s + posto1.toString() + ",";
            }
        }
        s = s + "]}";
        return s;
    }
}