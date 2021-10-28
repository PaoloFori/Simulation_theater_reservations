package com.prenotazioni.apigateway.Controllers;

import java.util.List;

import com.prenotazioni.apigateway.Exception.ExceptionNoUser;
import com.prenotazioni.apigateway.Services.ApiGatewayServiceCommand;
import com.prenotazioni.apigateway.Services.ApiGatewayServiceQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerQuery {

    @Autowired
    ApiGatewayServiceQuery apiGatewayServiceQuery;

    @Autowired
    ApiGatewayServiceCommand apiGatewayServiceCommand;

    /**
     * Get all users from DB
     * @return List of string with all user
     */
    @GetMapping(value = "/Users")
    public List<String> getAllUser(){
        return apiGatewayServiceQuery.getAllUser();
    }

    /**
     * Get a list of determinate users by name or surname or both
     * @param name: user's name
     * @param surname: user's surname
     * @return a list of users
     */
    @GetMapping(value = "/getUserByParam")
    public List<String> getUserByNameOrSurname(@RequestParam(value = "name", defaultValue = "")String name, @RequestParam(value = "surname", defaultValue = "")String surname){
        return apiGatewayServiceQuery.getUserByNameOrSurnameOrBoth(name, surname);
    } 
    
    /**
     * Get a determinate User by id
     * @param id: user's id
     * @return a string where I say if everything is fine
     */
    @GetMapping(value = "/getUserById")
    public String getUserById(@RequestParam(value = "id")int id){
        try{
            return apiGatewayServiceQuery.getUserById(id);
        }catch(ExceptionNoUser e){
            return "No user with id: " + id;
        }
    }
}