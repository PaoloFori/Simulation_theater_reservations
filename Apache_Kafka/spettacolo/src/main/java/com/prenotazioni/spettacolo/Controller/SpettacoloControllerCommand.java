package com.prenotazioni.spettacolo.Controller;

import java.util.List;

import com.prenotazioni.spettacolo.Entity.Posto;
import com.prenotazioni.spettacolo.Entity.Sala;
import com.prenotazioni.spettacolo.Entity.Spettacolo;
import com.prenotazioni.spettacolo.Exception.ExceptionNoPosto;
import com.prenotazioni.spettacolo.Exception.ExceptionNoSala;
import com.prenotazioni.spettacolo.Exception.ExceptionNoSpettacolo;
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
     * @param spettacolo: name of show
     * @return a string where I say if everything is fine
     */
    @PutMapping("/Teatro/addShow")
    public String insertNewShow(@RequestBody Spettacolo spettacolo){
        spettacolo.setAction("Add New");
        return spettacoloServiceCommand.insertNewSpettacolo(spettacolo);
    }

    /**
     * Insert New Show with sala already exists
     * @param spettacolo: name of show
     * @param costo: cost of show
     * @param numeroSale: list of number of sale
     * @return a string where I say if everything is fine
     */
    @PutMapping("/Teatro/addShow/nowSala")
    public String insertNewShowKnowSala(@RequestParam(value = "Show") String spettacolo, @RequestParam(value = "costo") int costo ,@RequestParam(value = "Lista delle sale") List<Integer> numeroSale){
        return spettacoloServiceCommand.insertNewSpettacoloWithSalaAlreadyExist(spettacolo, costo, numeroSale);
    }

    /**
     * Add new sala for the show
     * @param spettacolo: name of show
     * @param sala: sala for the show
     * @return a string where I say if everything is fine
     */
    @PostMapping("/Teatro/{show}/addNewSala")
    public String addNewSalaByShow(@PathVariable("show")String spettacolo, @RequestBody Sala sala){
        try{
            String s1 = "New Sala added correctly for the show " + spettacolo;
            String s = spettacoloServiceCommand.insertSalaBySpettacolo(spettacolo, sala);
            if(s.equals(s1)){
                Spettacolo spett = spettacoloServiceQuery.getSpettacolo(spettacolo);
                spett.setAction("Add New Sala");
                spettacoloServiceCommand.publishMessage(spett);
            }
            return s;
        }catch(ExceptionNoSpettacolo e){
            return "No Show with name: " + spettacolo;
        }
    }

    /**
     * Add sala already exists
     * @param spettacolo: name of show
     * @param numeroSala: list of sale
     * @return a string where I say if everything is fine
     */
    @PostMapping("/Teatro/{show}/addSala")
    public String addSalaAlreadyExistByShow(@PathVariable("show") String spettacolo, @RequestParam(value = "numeroSala") List<Integer> numeroSala){
        try{
            String s = spettacoloServiceCommand.insertExistSalaBySpettacolo(spettacolo, numeroSala);
            Spettacolo spett = spettacoloServiceQuery.getSpettacolo(spettacolo);
            spett.setAction("Add New Sala already exist");
            spettacoloServiceCommand.publishMessage(spett);
            return s;
        }catch(ExceptionNoSpettacolo e){
            return "No Show with name: " + spettacolo;
        }
    }

    /**
     * Delete show
     * @param spettacolo: name of show
     * @return a string where I say if everything is fine
     */
    @DeleteMapping("/Teatro/deleteShow")
    public String deleteEverythingByShow(@RequestParam(value = "show") String spettacolo){
        try{
            Spettacolo spett = spettacoloServiceQuery.getSpettacolo(spettacolo);
            spett.setAction("Delete Show");
            spettacoloServiceCommand.publishMessage(spett);
            String s = spettacoloServiceCommand.deleteEverythingByShow(spettacolo);
            return s;
        }catch(ExceptionNoSpettacolo e){
            return "No show with name: "+ spettacolo;
        }
    }

    /**
     * Delete a Sala by Show and numeroSala
     * @param show: name of show
     * @param numeroSala: number of sala
     * @return a string where I say if everything is fine
     */
    @DeleteMapping("/Teatro/{show}/deleteSala")
    public String deleteSalaByShowAndNumeroSala(@PathVariable("show")String show, @RequestParam(value= "numeroSala") int numeroSala){
        try{
            String s = spettacoloServiceCommand.deleteSalaByNumeroSalaAndShow(show, numeroSala);
            Spettacolo spett = spettacoloServiceQuery.getSpettacolo(show);
            spett.setAction("Delete Sala");
            spettacoloServiceCommand.publishMessage(spett);
            return s;
        }catch(ExceptionNoSala e){
            return "No Sala: "+numeroSala +" for show: " + show;
        }catch(ExceptionNoSpettacolo e1){
            return "No show: " + show + " in the DB!";
        }
    }
    
    /**
     * Upset Posto by numeroSala and show
     * @param spettacolo: name of show
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
            Spettacolo s = spettacoloServiceCommand.setPostoByTitoloAnNumeroSalaAndPosto(spettacolo, numeroSala, posto);
            s.setAction("Upset Posto");
            spettacoloServiceCommand.publishMessage(s);
            return s.toString1();
        }catch(ExceptionNoSala e){
            return "No Sala with numerSala: " + numeroSala;
        }catch(ExceptionNoSpettacolo e1){
            return "No show with name: " + spettacolo;
        }catch(ExceptionNoPosto e2){
            return "Not exists posto: " + riga.toUpperCase() + colonna + " for sala: " + numeroSala;
        }
    }
}