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
    UserServiceCommand userServiceCommand;

    @Autowired
    UserServiceQuery userServiceQuery;

    /**
     * We need to know the id. It use to change: name or surname or money.
     * @param id: id of user
     * @param name: name of user
     * @param surname: surname of user
     * @param cash: money of user
     * @return a string where I say if everything is fine
     */
    @PostMapping("/upsetUser")
    public String upset(@RequestParam("id") int id, @RequestParam(value = "name", defaultValue = "") String name, 
                        @RequestParam(value= "surname", defaultValue = "") String surname, 
                        @RequestParam(value= "money", defaultValue = "-1") int cash){
        try{ 
            String s = userServiceCommand.upset(id, name, surname, cash);
            User user = userServiceQuery.getUserById(id);
            user.setAction("Upset");
            userServiceCommand.publishMessage(user);
            return s;
        }catch(ExceptionNoUser e){
            return "No User with this id";
        }
    }

    /**
     * We need to know the param, not the ID, because it is specific for the single user
     * @param name: name of user
     * @param surname: surname of user
     * @param cash: cash of user
     * @return a string where I say if everything is fine
     */
    @PutMapping("/putNewUser")
    public String insert(@RequestParam(value = "name") String name, @RequestParam(value= "surname") String surname, @RequestParam(value= "money") int cash){
        List<User> userr = userServiceQuery.getAll();
        User user;
        if(userr.size() == 0){
            user = userr.get(0);
        }else{
            user = userr.get(userr.size() - 1);
        }
        int size = user.getId() + 1;
        User user1 = new User(size, name, surname, cash, "Add New");  
        userServiceCommand.publishMessage(user1);
        return userServiceCommand.insert(user1);
    }

    /**
     * Remove money from a User. 
     * @param id: id of user
     * @param cash: cash of user
     * @return a string where I say if everything is fine
     */
    @PostMapping("/{userId}/removeMoney")
    public String removeMoney(@PathVariable("userId") int id, @RequestParam(value = "money") int money){
        try{
            String s = userServiceCommand.removeCash(id, money);
            User user = userServiceQuery.getUserById(id);
            user.setAction("Remove Money");
            userServiceCommand.publishMessage(user);
            return s;
        }catch(ExceptionNoEnoughMoney e1){
            return "No enough money";
        }catch(ExceptionNoUser e2){
            return "No user with this id";
        }
    }

    /**
     * Add money to a specific User
     * @param id: id of user
     * @param money: money of user
     * @return a string where I say if everything is fine
     */
    @PostMapping("/{userId}/addMoney")
    public String addMoney(@PathVariable("userId") int id, @RequestParam(value = "money") int money){
        try{
            String s = userServiceCommand.addCash(id, money);
            User user = userServiceQuery.getUserById(id);
            user.setAction("Add Money");
            userServiceCommand.publishMessage(user);
            return s;
        }catch(ExceptionNoUser e){
            return "No user with this id";
        }
    }

    /**
     * Remove a determinate User
     * @param id: id of user
     * @return a string where I say if everything is fine
     */
    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam(value = "id")int id){
        try{
            User user = userServiceQuery.getUserById(id);
            String s = userServiceCommand.deleteById(id);
            user.setAction("Delete");
            userServiceCommand.publishMessage(user);
            return s;
        }catch(ExceptionNoUser e){
            return "no User with this id";
        }
    }
}