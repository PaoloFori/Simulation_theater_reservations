package com.prenotazioni.apigateway.Controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import com.prenotazioni.apigateway.Entity.Posto;
import com.prenotazioni.apigateway.Exception.ExceptionNoEnoughMoney;
import com.prenotazioni.apigateway.Exception.ExceptionNoFreePosto;
import com.prenotazioni.apigateway.Exception.ExceptionNoPosto;
import com.prenotazioni.apigateway.Exception.ExceptionNoSala;
import com.prenotazioni.apigateway.Exception.ExceptionNoSpettacolo;
import com.prenotazioni.apigateway.Exception.ExceptionNoUser;
import com.prenotazioni.apigateway.Services.ApiGatewayServiceCommand;
import com.prenotazioni.apigateway.Services.ApiGatewayServiceQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BigliettoController {

    @Autowired
    ApiGatewayServiceQuery apiGatewayServiceQuery;

    @Autowired
    ApiGatewayServiceCommand apiGatewayServiceCommand;

    ByteArrayOutputStream inputStream = null;

    /**
     * return last pdf created
     * @return last pdf created
     */
    @GetMapping("/Teatro/getLastTicket")
    public ResponseEntity<InputStreamResource> getLastTicket(){
        if(inputStream == null){
            return null;
        }
        ByteArrayInputStream input = new ByteArrayInputStream(inputStream.toByteArray());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(input));
    }

    /**
     * Book a posto
     * @param show: title of show
     * @param numeroSala: number of sala
     * @param idUser: user's id
     * @param riga: line of posto
     * @param colonna: column of posto
     * @return a string where I say if everything is fine
     */
    @PostMapping("/Teatro/{show}/{numeroSala}/prenota")
    public String prenotaPostoAndCreateTicket(@PathVariable("show")String show, @PathVariable("numeroSala")int numeroSala, @RequestParam(value = "idUser")int idUser, 
            @RequestParam(value = "riga") String riga, @RequestParam(value = "colonna") int colonna){
        try{
            Posto posto = new Posto(riga, colonna, false);
            inputStream = apiGatewayServiceQuery.getTicketAndSee(idUser, show, numeroSala, posto);
            return "Ticket ready, use the method getLastTicket to see";
        }catch(URISyntaxException e){
            return "Uri Sintax Exception";
        }catch(IOException e1){
            return "IOException";
        }catch(ExceptionNoUser e2){
            return "No User with id: " + idUser;
        }catch(ExceptionNoSpettacolo e3){
            return "No show with name: " + show;
        }catch(ExceptionNoSala e4){
            return "No sala: " + numeroSala + " for show: " + show;
        }catch(ExceptionNoEnoughMoney e5){
            return "User " + idUser + " haven't got enough money";
        }catch(ExceptionNoPosto e6){
            return "Not existx posto: " + colonna + riga.toUpperCase() + " for sala: " + numeroSala;
        }catch(ExceptionNoFreePosto e7){
            return "Posto: " + colonna + riga.toUpperCase() + " already busy";
        }
    }
}