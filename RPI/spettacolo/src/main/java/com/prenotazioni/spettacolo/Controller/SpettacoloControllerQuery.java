package com.prenotazioni.spettacolo.Controller;

import java.util.List;

import com.prenotazioni.spettacolo.Entity.Sala;
import com.prenotazioni.spettacolo.Entity.Spettacolo;
import com.prenotazioni.spettacolo.Exception.ExceptionNoSala;
import com.prenotazioni.spettacolo.Exception.ExceptionNoSpettacolo;
import com.prenotazioni.spettacolo.Services.SpettacoloServiceQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class SpettacoloControllerQuery {

    @Autowired
    SpettacoloServiceQuery spettacoloServiceQuery;

    /**
     * Show all shows
     * @return a list of all Spettacolo
     */
    @GetMapping(value ="/Teatro/shows")
    public List<Spettacolo> getAll(){
        return spettacoloServiceQuery.getAll();
    }

    /**
     * Show a determinate show and all sala for that show.
     * @param spettacolo: show's title
     * @return the show or null
     */
    @GetMapping(value = "/Teatro/{show}")
    public Spettacolo getShow(@PathVariable("show") String spettacolo){
        try{
            return spettacoloServiceQuery.getSpettacolo(spettacolo);
        }catch(ExceptionNoSpettacolo e){
            return null;
        }
    }

    /**
     * Show a determinate show and Sala
     * @param spettacolo: show's title
     * @param numeroSala: show's number of sala
     * @return the show or null
     */
    @GetMapping(value = "/Teatro/{show}/{numeroSala}")
    public Spettacolo getShowByShowAndNumeroSala(@PathVariable("show") String spettacolo, @PathVariable("numeroSala") int numeroSala){
        try{
            return spettacoloServiceQuery.getSpettacoloBySpettacoloAndNumeroSala(spettacolo, numeroSala);
        }catch(ExceptionNoSpettacolo e){ 
            return null;
        }catch(ExceptionNoSala e1){ 
            return null;
        }
    }

    /**
     * Get costo by the Show selected
     * @param spettacolo: show's title
     * @return a string where I say if everything is fine
     */
    @GetMapping(value="/Teatro/show")
    public String getCostoBySpettacolo(@RequestParam(value = "show") String spettacolo) {
        try{
            return "show: " + spettacolo + ", costs: " +spettacoloServiceQuery.getCostoBySpettacolo(spettacolo);
        }catch(ExceptionNoSpettacolo e){
            return "No show with name: "+ spettacolo;
        }
    }
    
    /**
     * Get all Sala for the show passed
     * @param spettacolo: show's title
     * @return list of all Sala for that show or null
     */
    @GetMapping(value = "/Teatro/{show}/sale")
    public List<Sala> getAllSalaBySpettacolo(@PathVariable("show") String spettacolo){
        try{
            return spettacoloServiceQuery.getSaleBySpettacolo(spettacolo);
        }catch(ExceptionNoSpettacolo e){
            return null;
        }
    }

    /**
     * Get all Free Posto in all Sala for that show
     * @param spettacolo: show's title
     * @return a Spettacolo with only all posto with free==true or null
     */
    @GetMapping(value = "/Teatro/{show}/freePosti")
    public Spettacolo getAllFreePostoPerAllSalaByShow(@PathVariable("show") String spettacolo){
        try{
            return spettacoloServiceQuery.getAllFreePostoPerAllSalaByShow(spettacolo);
        }catch(ExceptionNoSpettacolo e){
            return null;
        }
    }

    /**
     * Get all Free Posto for Sala and Show passed
     * @param spettacolo: show'title
     * @param numeroSala: number of sala
     * @return a Spettacolo with only all posto with free==true for that sala or null
     */
    @GetMapping(value = "/Teatro/{show}/{numeroSala}/freePosti")
    public Spettacolo getAllFreePostoByShowAndNumeroSala(@PathVariable("show")String spettacolo, @PathVariable("numeroSala") int numeroSala){
        try{
            return spettacoloServiceQuery.getAllFreePostoBySpettacoloAndNumeroSala(spettacolo, numeroSala);
        }catch(ExceptionNoSpettacolo e){ 
            return null;
        }catch(ExceptionNoSala e1){ 
            return null;
        }
    }
}