package com.prenotazioni.apigateway.Controllers;

import java.util.List;

import com.prenotazioni.apigateway.Exception.ExceptionNoSala;
import com.prenotazioni.apigateway.Exception.ExceptionNoSpettacolo;
import com.prenotazioni.apigateway.Services.ApiGatewayServiceCommand;
import com.prenotazioni.apigateway.Services.ApiGatewayServiceQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpettacoloControllerQuery {

    @Autowired
    ApiGatewayServiceQuery apiGatewayServiceQuery;

    @Autowired
    ApiGatewayServiceCommand apiGatewayServiceCommand;

    /**
     * Get all Show on DB
     * @return list of string with all show
     */
    @GetMapping(value = "/getAllShow")
    public List<String> getAllShow(){
        return apiGatewayServiceQuery.getAllShows();
    }

    /**
     * Get all shows by name or get a determinate show by name and numeroSala
     * @param show: title of show
     * @param numeroSala: number of sala
     * @return a string where I say if everything is fine
     */
    @GetMapping(value="/Teatro/{show}")
    public String getShow(@PathVariable("show")String show, @RequestParam(value = "numeroSala", defaultValue = "-1")int numeroSala){
        try{
            return apiGatewayServiceQuery.getShowByParam(show, numeroSala); 
        }catch(ExceptionNoSpettacolo e){
            return "No show with name: " + show;
        }catch(ExceptionNoSala e1){
            return "No sala: " + numeroSala + " for show: " + show;
        }
    }

    /**
     * Get cost for show
     * @param show: title of show
     * @return a string where I say if everything is fine
     */
    @GetMapping(value = "/Teatro/{show}/cost")
    public String getCostByShow(@PathVariable("show")String show){
        return apiGatewayServiceQuery.getCostoBySpettacolo(show);
    }

    /**
     * Get all free posto by show or for show and numeroSala
     * @param show: title of show
     * @param numeroSala: number of sala
     * @return a string where I say if everything is fine
     */
    @GetMapping(value = "/Teatro/{show}/FreePosti")
    public String getAllFreePostoByShowOrShowAndNumeroSala(@PathVariable("show") String show, @RequestParam(value = "numeroSala", defaultValue = "-1")int numeroSala){
        try{
            if(numeroSala>=0){
               return apiGatewayServiceQuery.getAllFreePostoByNumeroSalaAndShow(show, numeroSala);
            }else{
                return apiGatewayServiceQuery.getAllFreePostoPerAllSalaByShow(show);
            }
        }catch(ExceptionNoSpettacolo e){
            return "No show with name: " + show;
        }catch(ExceptionNoSala e1){
            return "No sala: " + numeroSala + " for show: " + show;
        }
    }
}