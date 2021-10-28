package com.prenotazioni.apigateway.Controllers;

import java.net.URISyntaxException;
import java.util.List;

import com.prenotazioni.apigateway.Entity.Posto;
import com.prenotazioni.apigateway.Entity.Sala;
import com.prenotazioni.apigateway.Entity.Spettacolo;
import com.prenotazioni.apigateway.Services.ApiGatewayServiceCommand;
import com.prenotazioni.apigateway.Services.ApiGatewayServiceQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpettacoloControllerCommand {

    @Autowired
    ApiGatewayServiceQuery apiGatewayServiceQuery;

    @Autowired
    ApiGatewayServiceCommand apiGatewayServiceCommand;

    /**
     * Delete a determinate show
     * @param show: title of show
     * @return a string where I say if everything is fine
     */
    @DeleteMapping("/Teatro/deleteShow")
    public String deleteByShow(@RequestParam(value = "show") String show){
        try{
            return apiGatewayServiceCommand.deleteByShow(show);
        }catch(URISyntaxException e){
            return "Uri sintax error";
        }
    }

    /**
     * Insert a new Show
     * @param spettacolo: all features of the new show
     * @return a string where I say if everything is fine
     */
    @PutMapping("/Teatro/addNewShowAndNewSale")
    public String insertNewShowAndNewSala(@RequestBody Spettacolo spettacolo){
        try{
            return apiGatewayServiceCommand.inserNewShowAndNewSala(spettacolo);
        }catch(URISyntaxException e){
            return "Uri sintax error";
        }
    }

    /**
     * Insert new Show knowing numeroSale
     * @param show: title of show
     * @param cost: cost of show
     * @param listNumeroSale: list of number of sale for the show
     * @return a string where I say if everything is fine
     */
    @PutMapping("/Teatro/addNewShowAndKnowSale")
    public String insertNewShowKnowSala(@RequestParam(value = "show") String show, @RequestParam(value = "cost") int cost, 
            @RequestParam(value = "listNumeroSale") List<Integer> listNumeroSale){
        try{
            return apiGatewayServiceCommand.inserNewShowAndKnowSala(show, cost, listNumeroSale);
        }catch(URISyntaxException e){
            return "Uri sintax error";
        }
    }

    /**
     * Delete a deterinate sala for the show
     * @param show: name of show
     * @param numeroSala: number of sala
     * @return a string where I say if everything is fine
     */
    @DeleteMapping("/Teatro/{show}/deleteSala")
    public String deleteSalaByShowAndNumeroSala(@PathVariable("show")String show, @RequestParam(value = "numeroSala")int numeroSala){
        try{
            return apiGatewayServiceCommand.deleteSalaByShowAndNumeroSala(show, numeroSala);
        }catch(URISyntaxException e){
            return "Uri sintax error";
        }
    }

    /**
     * Add a new Sala for that show
     * @param show: title of show
     * @param sala: new sala to add
     * @return a string where I say if everything is fine
     */
    @PostMapping("/Teatro/{show}/addNewSala")
    public String addNewSalaByShow(@PathVariable("show") String show, @RequestBody Sala sala){
        try{
            return apiGatewayServiceCommand.addNewSalaByShow(show, sala);
        }catch(URISyntaxException e){
            return "Uri sintax error";
        }
    }

    /**
     * Add a exists Sala for that show
     * @param show: title of show
     * @param sala: list of sala already exists to add
     * @return a string where I say if everything is fine
     */
    @PostMapping("/Teatro/{show}/addExistSala")
    public String addExistsSalaByShow(@PathVariable("show") String show, @RequestParam(value = "ListNumeroSale") List<Integer> numeroSale){
        try{
            return apiGatewayServiceCommand.addExistsSalaByShow(show, numeroSale);
        }catch(URISyntaxException e){
            return "Uri sintax error";
        }
    }

    /**
     * Set Posto
     * @param show: title of show
     * @param numeroSala: number of sala
     * @param free: state of posto
     * @param riga: line of posto
     * @param colonna: column of posto
     * @return a string where I say if everything is fine
     */
    @PostMapping("/Teatro/{show}/{numeroSala}/setPosto")
    public String setPosto(@PathVariable("show")String show, @PathVariable("numeroSala")int numeroSala, @RequestParam(value = "free")boolean free, 
            @RequestParam(value = "riga") String riga, @RequestParam(value = "colonna") int colonna){
        try{
            Posto posto = new Posto(riga, colonna, free);
            return apiGatewayServiceCommand.setPosto(show, numeroSala, posto);
        }catch(URISyntaxException e){
            return "Sintax error";
        }
    }
}