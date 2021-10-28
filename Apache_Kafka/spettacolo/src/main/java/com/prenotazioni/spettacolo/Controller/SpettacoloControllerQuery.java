package com.prenotazioni.spettacolo.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.prenotazioni.spettacolo.Entity.Sala;
import com.prenotazioni.spettacolo.Entity.Spettacolo;
import com.prenotazioni.spettacolo.Exception.ExceptionNoSala;
import com.prenotazioni.spettacolo.Exception.ExceptionNoSpettacolo;
import com.prenotazioni.spettacolo.Services.SpettacoloServiceQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpettacoloControllerQuery {

    @Autowired
    SpettacoloServiceQuery spettacoloServiceQuery;

    /**
     * Show all shows
     * @return list with all show
     */
    @GetMapping(value ="/Teatro/shows")
    public List<Spettacolo> getAll(){
        return spettacoloServiceQuery.getAll();
    }
    
    /**
     * Show a determinate show.
     * @param spettacolo: name of show
     * @return a string where I say if everything is fine
     */
    @GetMapping(value = "/Teatro/{show}")
    public String getShow(@PathVariable("show") String spettacolo){
        try{
            return spettacoloServiceQuery.getSpettacolo(spettacolo).toString();
        }catch(ExceptionNoSpettacolo e){
            return "No Spettacolo with name: " + spettacolo;
        }
    }

    /**
     * Show a determinate show and Sala
     * @param spettacolo: name of show
     * @param numeroSala: number of sala
     * @return a string where I say if everything is fine
     */
    @GetMapping(value = "/Teatro/{show}/{numeroSala}")
    public String getShowByShowAndNumeroSala(@PathVariable("show") String spettacolo, @PathVariable("numeroSala") int numeroSala){
        try{
            return spettacoloServiceQuery.getSpettacoloBySpettacoloAndNumeroSala(spettacolo, numeroSala).toString();
        }catch(ExceptionNoSpettacolo e){
            return "No show with name: " + spettacolo;
        }catch(ExceptionNoSala e1){
            return "No sala: " + numeroSala + " for show: " + spettacolo;
        }
    }

    /**
     * Get costo by the Show selected
     * @param spettacolo: name of show
     * @return a string where I say if everything is fine
     */
    @GetMapping(value="/Teatro/show")
    public String getCostoBySpettacolo(@RequestParam(value = "show") String spettacolo) {
        try{
            return "show: "+spettacolo+", costs: " + spettacoloServiceQuery.getCostoBySpettacolo(spettacolo);
        }catch(ExceptionNoSpettacolo e){
            return "No Show with name: " + spettacolo;
        }
    }
    
    /**
     * Get all Sala for the show passed
     * @param spettacolo: name of show
     * @return a string where I say if everything is fine
     */
    @GetMapping(value = "/Teatro/{show}/sale")
    public List<String> getAllSalaBySpettacolo(@PathVariable("show") String spettacolo){
        try{
            List<String> listOfSala = new ArrayList<>();
            for(Sala sala : spettacoloServiceQuery.getSaleBySpettacolo(spettacolo)){
                listOfSala.add(sala.toString());
            }
            return listOfSala;
        }catch(ExceptionNoSpettacolo e){
            return Arrays.asList("No spettacolo with name: " + spettacolo);
        }
    }

    /**
     * Get all Free Posto in all Sala for that show
     * @param spettacolo: name of show
     * @return a string where I say if everything is fine
     */
    @GetMapping(value = "/Teatro/{show}/freePosti")
    public String getAllFreePostoPerAllSalaByShow(@PathVariable("show") String spettacolo){
        try{
            return spettacoloServiceQuery.getAllFreePostoPerAllSalaByShow(spettacolo).toString();
        }catch(ExceptionNoSpettacolo e){
            return "No spettacolo with name: " + spettacolo;
        }
    }

    /**
     * Get all Free Posto for Sala and Show passed
     * @param spettacolo: name of show
     * @param numeroSala: number of sala
     * @return a string where I say if everything is fine
     */
    @GetMapping(value = "/Teatro/{show}/{numeroSala}/freePosti")
    public String getAllFreePostoByShowAndNumeroSala(@PathVariable("show")String spettacolo, @PathVariable("numeroSala") int numeroSala){
        try{
            return spettacoloServiceQuery.getAllFreePostoBySpettacoloAndNumeroSala(spettacolo, numeroSala).toString();
        }catch(ExceptionNoSpettacolo e){
            return "No spettacolo with name: " + spettacolo;
        }catch(ExceptionNoSala e1){
            return "No sala: " + numeroSala + " for show: " + spettacolo;
        }
    }
}