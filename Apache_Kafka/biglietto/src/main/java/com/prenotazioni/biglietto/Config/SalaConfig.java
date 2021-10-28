package com.prenotazioni.biglietto.Config;

import java.util.ArrayList;
import java.util.List;

/*import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;*/
import lombok.Getter;
import lombok.Setter;

//@Entity(name = "SALA")
public class SalaConfig {

    /*@Id
    @Column(name = "ID")
    @GeneratedValue
    @Getter
    private Long id;*/

    @Getter @Setter
    //@Column(name = "NUMEROSALA")
    private int numeroSala;

    @Getter @Setter 
    /*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) 
    @Column(name = "POSTO")*/
    private List<PostoConfig> posto = new ArrayList<>();

    public SalaConfig(){
    }

    public SalaConfig(int numeroSala, List<PostoConfig> posto){
        this.numeroSala = numeroSala;
        this.posto = posto;
    }

    @Override
    public String toString(){
        String s = "{\"numeroSala\":"+numeroSala+",\n\"posto\":[";
        for(int i = 0; i < posto.size(); i++){
            PostoConfig posto1 = posto.get(i);
            if(i == posto.size()-1){
                s = s + posto1.toString();
            }else{
                s = s + posto1.toString() + ",";
            }
        }
        s = s + "]}";
        return s;
    }
}