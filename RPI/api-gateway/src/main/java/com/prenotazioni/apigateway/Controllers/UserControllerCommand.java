package com.prenotazioni.apigateway.Controllers;

import java.net.URISyntaxException;

import com.prenotazioni.apigateway.Services.ApiGatewayServiceCommand;
import com.prenotazioni.apigateway.Services.ApiGatewayServiceQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerCommand {
    
    @Autowired
    ApiGatewayServiceQuery apiGatewayServiceQuery;

    @Autowired
    ApiGatewayServiceCommand apiGatewayServiceCommand;

    /**
     * Insert a new User
     * @param name: user's name
     * @param surname: user's surname
     * @param saldo: user's cash
     * @return a string where I say if everything is fine
     */
    @PutMapping(value = "/insert/newUser")
    public String putNewUser(@RequestParam(value = "name")String name, @RequestParam(value = "surname") String surname, @RequestParam(value = "soldi")int saldo){
        try{
            return apiGatewayServiceCommand.putNewUser(name, surname, saldo);
        }catch(URISyntaxException e){
            return "Error sintax uri";
        }
    }

     /**
     * Add money to a specific user
     * @param id: user's id
     * @param money: money to remove
     * @return a string where I say if everything is fine
     */
    @PostMapping("/{idUser}/removeMoney")
    public String removeMoneybyUserId(@PathVariable("idUser") int id, @RequestParam(value = "money") int money){
        try{
            return apiGatewayServiceCommand.removeMoneyUser(id, money);
        }catch(URISyntaxException e){
            return "Error sintax uri";
        }
    }

    /**
     * Upset a specific User,  string is optional with "" while integer with -1
     * @param id: user's id
     * @param cash: user's cash
     * @param name: user's name
     * @param surname: user's surname
     * @return a string where I say if everything is fine
     */
    @PostMapping("/{idUser}/upset")
    public String upsetUser(@PathVariable("idUser") int id, @RequestParam(value = "money", defaultValue = "-1") int cash, 
                @RequestParam(value = "name", defaultValue = "") String name,
                @RequestParam(value = "surname", defaultValue = "")String surname){
        try{
            return apiGatewayServiceCommand.upsetUser(id, name, surname, cash);
        }catch(URISyntaxException e){
            return "Error sintax uri";
        }
    }
    
    /**
     * Remove cash for a specific person
     * @param idUser: user's id
     * @param money: money to add
     * @return a string where I say if everything is fine
     */
    @PostMapping("/{idUser}/addMoney")
    public String addMoneyByUserId(@PathVariable("idUser")int idUser, @RequestParam(value ="money") int money){
        try{
            return apiGatewayServiceCommand.addMoneyUser(idUser, money);
        }catch(URISyntaxException e){
            return "Error sintax uri";
        }
    }

    /**
     * Delete a user
     * @param id: user's id
     * @return a string where I say if everything is fine
     */
    @DeleteMapping("/deleteUser")
    public String deleteUserById(@RequestParam("idUser")int id){
        try{
            return apiGatewayServiceCommand.deleteUser(id);
        }catch(URISyntaxException e){
            return "Error sintax uri";
        }
    }
}