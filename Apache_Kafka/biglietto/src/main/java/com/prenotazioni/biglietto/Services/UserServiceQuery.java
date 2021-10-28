package com.prenotazioni.biglietto.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.prenotazioni.biglietto.Entity.User;
import com.prenotazioni.biglietto.Exceptions.ExceptionNoUser;
import com.prenotazioni.biglietto.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceQuery {
    
    @Autowired
	UserRepository userRepository;

     /**
     * Ritorna tutti gli utenti presenti nel database
     * 
     * @return
     */
    public List<String> getAll() {
        List<String> listOfUser = new ArrayList<>();
        for(User user : userRepository.findAll()){
            listOfUser.add(user.toString());
        }
        return listOfUser;
    }

    /**
     * Ritorna determinati utenti per nome o cognome, occhio alle maiuscole
     * 
     * @param name
     * @return
     */
    public List<String> getByName(String name) {
        List<String> listOfUser = new ArrayList<>();
        for(User user : userRepository.findByName(name)){
            listOfUser.add(user.toString());
        }
        return listOfUser;
    }
    public List<String> getBySurname(String surname) {
        List<String> listOfUser = new ArrayList<>();
        for(User user : userRepository.findBySurname(surname)){
            listOfUser.add(user.toString());
        }
        return listOfUser;
    }

     /**
     * Ritorna l'utente se esso c'è
     * @param id
     * @return
     * @throws ExceptionNoUser
     */
    public String getUserById(int id) throws ExceptionNoUser{
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get().toString();   
        }
        throw new ExceptionNoUser();
    }

    public List<String> getAllUserByNameAndSurname(String name, String surname){
        List<User> userName = userRepository.findByName(name);
        List<User> user = new ArrayList<>();
        List<String> listOfUser = new ArrayList<>();
        User user1;
        for(int i = 0; i < userName.size(); i++){
            user1 = userName.get(i);
            if(user1.getSurname().equals(surname)){
                user.add(user1);
            }
        }
        for(User user2 : user){
            listOfUser.add(user2.toString());
        }
        return listOfUser;
    }
}