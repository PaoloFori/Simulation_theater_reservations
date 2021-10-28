package com.prenotazioni.utente.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.prenotazioni.utente.Entity.User;
import com.prenotazioni.utente.Exception.ExceptionNoUser;
import com.prenotazioni.utente.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceQuery {
    @Autowired
    UserRepository userRepository;

    /**
     * Ritorna tutti gli utenti presenti nel database
     * @return lista di tutti gli utenti
     */
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Ritorna determinati utenti
     * @param name: nome dell'utente
     * @return ritorna una lista con gli utenti con lo stesso nome
     */
    public List<User> getByName(String name) {
        return userRepository.findByName(name);
    }
    
    /**
     * Ritorna determinati utenti
     * @param surname: cognome dell'utente
     * @return ritorna una lista con gli utenti con lo stesso cognome
     */
    public List<User> getBySurname(String surname) {
        return userRepository.findBySurname(surname);
    }

     /**
     * Ritorna l'utente se esso c'è
     * @param id: id dell'utente
     * @return ritorna l'utente
     * @throws ExceptionNoUser: se l'utente non è presente
     */
    public User getUserById(int id) throws ExceptionNoUser{
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();   
        }
        throw new ExceptionNoUser();
    }

    /**
     * Ritorna tutti gli utenticon stesso nome e cognome
     * @param name: nome degli utenti
     * @param surname: cognome degli utenti
     * @return lista di utenti o lista vuota
     */
    public List<User> getAllUserByNameAndSurname(String name, String surname){
        List<User> userName = userRepository.findByName(name);
        List<User> user = new ArrayList<>();
        User user1;
        for(int i = 0; i < userName.size(); i++){
            user1 = userName.get(i);
            if(user1.getSurname().equals(surname)){
                user.add(user1);
            }
        }
        return user;
    }
}