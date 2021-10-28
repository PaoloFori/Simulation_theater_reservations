package com.prenotazioni.utente.Controller;

import java.util.List;

import com.prenotazioni.utente.Entity.User;
import com.prenotazioni.utente.Exception.ExceptionNoUser;
import com.prenotazioni.utente.Services.UserServiceQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerQuery {
    
    @Autowired
    UserServiceQuery userServiceQuery;
    
     /**
     * Return all user with the same surname
     * @param surname: surname of user
     * @return a string where I say if everything is fine
     */
    @GetMapping("/userSurname")
    public List<User> getAllUserBySurname(@RequestParam(value = "surname") String surname){
        return userServiceQuery.getBySurname(surname);
    }

    /**
     * Return a single User with that id
     * @param id: id of user
     * @return a string where I say if everything is fine
     */
    @GetMapping("/userId")
    public String getUserById(@RequestParam(value = "userId") int id){
        try{
            return userServiceQuery.getUserById(id).toString();
        }catch(ExceptionNoUser e){
            return "No user with this id";
        }
    }

    /**
     * Return all the user in the DB
     * @return a string where I say if everything is fine
     */
    @GetMapping("/users")
    public List<User> getAllUser(){
        return userServiceQuery.getAll();
    }
    
    /**
     * Return all user with the same name
     * @param name: name of user
     * @return a string where I say if everything is fine
     */
    @GetMapping("/userName")
    public List<User> getAllUserByName(@RequestParam(value ="name") String name){
        return userServiceQuery.getByName(name);
    }

    /**
     * Return all user with the same name and surname
     * @param name: name of user
     * @param surname: surname of user
     * @return a string where I say if everything is fine
     */
    @GetMapping("/userNameAndSurname")
    public List<User> getAllUserByNameAndSurname(@RequestParam(value ="name") String name, @RequestParam(value = "surname")String surname){
        return userServiceQuery.getAllUserByNameAndSurname(name, surname);
    }
}