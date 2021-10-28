package com.prenotazioni.biglietto.Controllers;

import java.util.Arrays;
import java.util.List;

import com.prenotazioni.biglietto.Exceptions.ExceptionNoSala;
import com.prenotazioni.biglietto.Exceptions.ExceptionNoSpettacolo;
import com.prenotazioni.biglietto.Services.SpettacoloServiceQuery;

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
     * @return
     */
    @GetMapping(value ="/Teatro/shows")
    public List<String> getAll(){
        return spettacoloServiceQuery.getAll();
    }
    
    /**
     * Show a determinate show.
     * @param spettacolo
     * @return the show or nothing
     */
    @GetMapping(value = "/Teatro/{show}")
    public String getShow(@PathVariable("show") String spettacolo){
        try{
            return spettacoloServiceQuery.getSpettacolo(spettacolo);
        }catch(ExceptionNoSpettacolo e){
            return "No Spettacolo with name: " + spettacolo;
        }
    }

    /**
     * Show a determinate show and Sala
     * @param spettacolo
     * @param numeroSala
     * @return
     */
    @GetMapping(value = "/Teatro/{show}/{numeroSala}")
    public String getShowByShowAndNumeroSala(@PathVariable("show") String spettacolo, @PathVariable("numeroSala") int numeroSala){
        try{
            return spettacoloServiceQuery.getSpettacoloBySpettacoloAndNumeroSala(spettacolo, numeroSala);
        }catch(ExceptionNoSpettacolo e){
            return "No spettacolo with name: " + spettacolo;
        }catch(ExceptionNoSala e1){
            return "No sala: " + numeroSala + " for show: " + spettacolo;
        }
    }

    /**
     * Get costo by the Show selected
     * @param spettacolo
     * @return
     */
    @GetMapping(value="/Teatro/show")
    public int getCostoBySpettacolo(@RequestParam(value = "show") String spettacolo) {
        try{
            return spettacoloServiceQuery.getCostoBySpettacolo(spettacolo);
        }catch(ExceptionNoSpettacolo e){
            return 0;
        }
    }
    
    /**
     * Get all Sala for the show passed
     * @param spettacolo
     * @return
     */
    @GetMapping(value = "/Teatro/{show}/sale")
    public List<String> getAllSalaBySpettacolo(@PathVariable("show") String spettacolo){
        try{
            return spettacoloServiceQuery.getSaleBySpettacolo(spettacolo);
        }catch(ExceptionNoSpettacolo e){
            return Arrays.asList("No spettacolo with name: " + spettacolo);
        }
    }

    /**
     * Get all Free Posto in all Sala for that show
     * @param spettacolo
     * @return
     */
    @GetMapping(value = "/Teatro/{show}/freePosti")
    public String getAllFreePostoPerAllSalaByShow(@PathVariable("show") String spettacolo){
        try{
            return spettacoloServiceQuery.getAllFreePostoPerAllSalaByShow(spettacolo);
        }catch(ExceptionNoSpettacolo e){
            return "No spettacolo with name: " + spettacolo;
        }
    }

    /**
     * Get all Free Posto for Sala and Show passed
     * @param spettacolo
     * @param numeroSala
     * @return
     */
    @GetMapping(value = "/Teatro/{show}/{numeroSala}/freePosti")
    public String getAllFreePostoByShowAndNumeroSala(@PathVariable("show")String spettacolo, @PathVariable("numeroSala") int numeroSala){
        try{
            return spettacoloServiceQuery.getAllFreePostoBySpettacoloAndNumeroSala(spettacolo, numeroSala);
        }catch(ExceptionNoSpettacolo e){
            return "No spettacolo with name: " + spettacolo;
        }catch(ExceptionNoSala e1){
            return "No sala: " + numeroSala + "for show: " + spettacolo;
        }
    }
}