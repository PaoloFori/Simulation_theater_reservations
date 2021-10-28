package com.prenotazioni.biglietto.Controllers;

import java.util.List;

import com.prenotazioni.biglietto.Exceptions.ExceptionNoUser;
import com.prenotazioni.biglietto.Services.UserServiceQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerQuery {
    
    @Autowired
    UserServiceQuery userServiceQuery;
    
     /**
     * Return all user with the param surname
     * @param surname
     * @return
     */
    @GetMapping("/userSurname")
    public List<String> getAllUserBySurname(@RequestParam(value = "surname") String surname){
        return userServiceQuery.getBySurname(surname);
    }

    /**
     * Return a single User with that id, or a empty User if the id isn't valid
     * @param id
     * @return
     */
    @GetMapping("/userId")
    public String getUserById(@RequestParam(value = "userId") int id){
        try{
            return userServiceQuery.getUserById(id);
        }catch(ExceptionNoUser e){
            return "No user with this id";
        }
    }

    /**
     * Return all the user in the DB
     * @return
     */
    @GetMapping("/users")
    public List<String> getAllUser(){
        return userServiceQuery.getAll();
    }
    
    /**
     * Return all user with the param name
     * @param name
     * @return
     */
    @GetMapping("/userName")
    public List<String> getAllUserByName(@RequestParam(value ="name") String name){
        return userServiceQuery.getByName(name);
    }

    /**
     * Return all user with the param name
     * @param name
     * @return
     */
    @GetMapping("/userNameAndSurname")
    public List<String> getAllUserByNameAndSurname(@RequestParam(value ="name") String name, @RequestParam(value = "surname")String surname){
        return userServiceQuery.getAllUserByNameAndSurname(name, surname);
    }
}