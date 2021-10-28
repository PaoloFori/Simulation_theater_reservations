package com.prenotazioni.spettacolo.Controller;

import java.util.List;

import com.prenotazioni.spettacolo.Entity.Posto;
import com.prenotazioni.spettacolo.Entity.Sala;
import com.prenotazioni.spettacolo.Entity.Spettacolo;
import com.prenotazioni.spettacolo.Exception.ExceptionNoSala;
import com.prenotazioni.spettacolo.Exception.ExceptionNoSpettacolo;
import com.prenotazioni.spettacolo.Exception.ExceptionNoPosto;
import com.prenotazioni.spettacolo.Services.SpettacoloServiceCommand;
import com.prenotazioni.spettacolo.Services.SpettacoloServiceQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class SpettacoloControllerCommand {
    @Autowired
    SpettacoloServiceCommand spettacoloServiceCommand;

    @Autowired
    SpettacoloServiceQuery spettacoloServiceQuery;

    /**
     * Insert a New Show
     * @param spettacolo: new show to add
     * @return a string where I say if everything is fine
     */
    @PutMapping("/Teatro/addNewShowAndNewSala")
    public String insertNewShow(@RequestBody Spettacolo spettacolo){
        return spettacoloServiceCommand.insertNewSpettacoloAndSala(spettacolo);
    }

    /**
     * Insert a New Show knowning sale
     * @param show: title of show
     * @param cost: cost of show
     * @param numeroSale: list of all sale to add
     * @return a string where I say if everything is fine
     */
    @PutMapping("/Teatro/addNewShowKnowSala")
    public String insertNewShowKnewSala(@RequestParam(value = "show")String show, @RequestParam(value = "cost") int cost, 
            @RequestParam(value = "listNumeroSale") List<Integer> numeroSale){
        return spettacoloServiceCommand.insertNewShowWithExistsSala(show, cost, numeroSale);
    }

    /**
     * Add new sala for the show
     * @param spettacolo: title of show
     * @param sala: new sala to add
     * @return a string where I say if everything is fine
     */
    @PostMapping("/Teatro/{show}/addNewSala")
    public String addSalaByShow(@PathVariable("show")String spettacolo, @RequestBody Sala sala){
        try{
            return spettacoloServiceCommand.insertSalaBySpettacolo(spettacolo, sala);
        }catch(ExceptionNoSpettacolo e){
            return "No Show with this name " + spettacolo;
        }
    }

    /**
     * Add a sala that already exists
     * @param spettacolo: title of show
     * @param numeroSale: list of all sala to add
     * @return a string where I say if everything is fine
     */
    @PostMapping("/Teatro/{show}/addExistSala")
    public String addExistsSalaByShow(@PathVariable("show")String spettacolo, @RequestParam(value = "listaNumeroSale") List<Integer> numeroSale){
        try{
            return spettacoloServiceCommand.insertAlreadyExistsSalaByShow(spettacolo, numeroSale);
        }catch(ExceptionNoSpettacolo e){
            return "No Show with this name " + spettacolo;
        }
    }

    /**
     * Delete show
     * @param spettacolo: title of show
     * @return a string where I say if everything is fine
     */
    @DeleteMapping("/Teatro/deleteShow")
    public String deleteEverythingByShow(@RequestParam(value = "show") String spettacolo){
        try{
            return spettacoloServiceCommand.deleteEverythingByShow(spettacolo);
        }catch(ExceptionNoSpettacolo e){
            return "No Show with this name "+ spettacolo;
        }
    }

    /**
     * Delete a Sala by Show and numeroSala
     * @param show: title of show
     * @param numeroSala: number of sala to delete
     * @returna string where I say if everything is fine
     */
    @DeleteMapping("/Teatro/{show}/deleteSala")
    public String deleteSalaByShowAndNumeroSala(@PathVariable("show")String show, @RequestParam(value= "numeroSala") int numeroSala){
        try{
            return spettacoloServiceCommand.deleteSalaByNumeroSalaAndShow(show, numeroSala);
        }catch(ExceptionNoSala e){
            return "No Sala: "+numeroSala +" for show: " + show;
        }catch(ExceptionNoSpettacolo e1){
            return "No Show with this name "+ show;
        }
    }

    /**
     * Upset Posto by numeroSala and show
     * @param spettacolo: name of Show
     * @param numeroSala: number of sala
     * @param colonna: column of posto
     * @param riga: line of posto
     * @param free: state of posto
     * @return a string where I say if everything is fine
     */
    @PostMapping("/Teatro/{show}/{numeroSala}/upsetPosto")
    public String upsetPostoByShowAndNumeroSala(@PathVariable("show")String spettacolo, @PathVariable("numeroSala") int numeroSala, 
            @RequestParam(value = "colonna") int colonna, @RequestParam(value = "riga")String riga, @RequestParam(value = "free") boolean free){
        try{
            Posto posto = new Posto(riga, colonna, free);
            return spettacoloServiceCommand.setPostoByTitoloAnNumeroSalaAndPosto(spettacolo, numeroSala, posto).toString();
        }catch(ExceptionNoSala e){
            return "No sala with numeroSala: " + numeroSala;
        }catch(ExceptionNoSpettacolo e1){
            return "No show with name: " + spettacolo;
        }catch(ExceptionNoPosto e2){
            return "Not exists posto: " + riga.toUpperCase() + colonna + " for sala: " + numeroSala;
        }
    }
}