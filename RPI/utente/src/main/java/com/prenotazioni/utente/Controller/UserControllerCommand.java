package com.prenotazioni.utente.Controller;

import java.util.List;

import com.prenotazioni.utente.Entity.User;
import com.prenotazioni.utente.Exception.ExceptionNoEnoughMoney;
import com.prenotazioni.utente.Exception.ExceptionNoUser;
import com.prenotazioni.utente.Services.UserServiceCommand;
import com.prenotazioni.utente.Services.UserServiceQuery;

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
    UserServiceQuery userServiceQuery;

    @Autowired
    UserServiceCommand userServiceCommand;

    /**
     * We need to know the id. It use to change: name or surname or money.
     * @param id: user's id
     * @param name: user's name
     * @param surname: user's surname
     * @param cash: user's cash
     * @return a string where I say if everything is fine
     */
    @PostMapping("/upsetUser")
    public String upset(@RequestParam("id") int id, @RequestParam(value = "name", defaultValue = "") String name, 
                        @RequestParam(value= "surname", defaultValue = "") String surname, 
                        @RequestParam(value= "money", defaultValue = "-1") int cash){
        try{ 
            return userServiceCommand.upset(id, name, surname, cash);
        }catch(ExceptionNoUser e){
            return "No User with this id";
        }
    }

    /**
     * We need to know the param, not the ID, because it is specific for the single user
     * @param name: user's name
     * @param surname: user's surname
     * @param cash: user's cash
     * @return a string where I say if everything is fine
     */
    @PutMapping("/putNewUser")
    public String insert(@RequestParam(value = "name") String name, @RequestParam(value= "surname") String surname, @RequestParam(value= "money") int cash){
        List<User> userr = userServiceQuery.getAll();
        User user = userr.get(userr.size() - 1);
        int size = user.getId() + 1;
        return userServiceCommand.insert(size, name, surname, cash);
    }

    /**
     * Remove money from a User.
     * @param id: user's id
     * @param cash: money to remove
     * @return a string where I say if everything is fine
     */
    @PostMapping("/{userId}/removeMoney")
    public String removeMoney(@PathVariable("userId") int id, @RequestParam(value = "money") int money){
        try{
            return userServiceCommand.removeCash(id, money);
        }catch(ExceptionNoEnoughMoney e1){
            return "No enough money";
        }catch(ExceptionNoUser e2){
            return "No user with this id";
        }
    }

    /**
     * Add money to a specific User
     * @param id: user's id
     * @param money: money to add
     * @return a string where I say if everything is fine
     */
    @PostMapping("/{userId}/addMoney")
    public String addMoney(@PathVariable("userId") int id, @RequestParam(value = "money") int money){
        try{
            return userServiceCommand.addCash(id, money);
        }catch(ExceptionNoUser e){
            return "No user with this id";
        }
    }

    /**
     * Remove a determinate User
     * @param id: user's id
     * @return a string where I say if everything is fine
     */
    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam(value = "id")int id){
        try{
            return userServiceCommand.deleteById(id);
        }catch(ExceptionNoUser e){
            return "no User with this id";
        }
    }
}