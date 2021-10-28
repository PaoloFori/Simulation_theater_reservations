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
     * Return all the user in the DB
     * @return a list of User 
     */
    @GetMapping("/users")
    public List<User> getAllUser(){
        return userServiceQuery.getAll();
    }
    
    /**
     * Return all user with the param name
     * @param name: user's name
     * @return a list of User 
     */
    @GetMapping("/userName")
    public List<User> getAllUserByName(@RequestParam(value ="name") String name){
        return userServiceQuery.getByName(name);
    }

    /**
     * Return all user with the param surname
     * @param surname: user's surname
     * @return a list of User 
     */
    @GetMapping("/userSurname")
    public List<User> getAllUserBySurname(@RequestParam(value = "surname") String surname){
        return userServiceQuery.getBySurname(surname);
    }

    /**
     * Return a single User with that id, or a empty User if the id isn't valid
     * @param id: user's id
     * @return a User if exist, alse return null
     */
    @GetMapping("/userId")
    public User getUserById(@RequestParam(value = "id") int id){
        try{
            return userServiceQuery.getUserById(id);
        }catch(ExceptionNoUser e){
            return null;
        }
    }
}