package com.prenotazioni.biglietto.Config;

/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;*/
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import lombok.Getter;
import lombok.Setter;

//@Entity(name = "POSTO")
public class PostoConfig {

    /*@Id 
    @Column(name = "ID")
    @GeneratedValue
    private Long id;*/

    @Getter @Setter 
    @NotNull @Size(min =1, max =1, message = "only one letter") 
    //@Column(name ="RIGA")
    private String riga;

    @Getter @Setter 
    @NotNull @Min(1) @Max(2) 
    //@Column(name ="COLONNA")
    private int colonna;

    //@Column(name ="FREE")
    private boolean free;

    public PostoConfig(String riga, int colonna, boolean free){
        this.riga = riga;
        this.colonna = colonna;
        this.free = free;
    }

    public PostoConfig(){}

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