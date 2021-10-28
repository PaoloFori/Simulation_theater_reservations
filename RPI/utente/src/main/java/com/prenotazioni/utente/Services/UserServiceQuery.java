package com.prenotazioni.utente.Services;

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
     * 
     * @return lista di Utenti presenti nel database
     */
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Ritorna determinati utenti per nome
     * 
     * @param name: nome degli utenti
     * @return lista di utenti con tutti lo stesso nome
     */
    public List<User> getByName(String name) {
        return userRepository.findByName(name);
    }
    
    /**
     * Ritorna determinati utenti per cognome
     * @param surname: cognome degli utenti
     * @return lista di utenti con tutti lo stesso cognome
     */
    public List<User> getBySurname(String surname) {
        return userRepository.findBySurname(surname);
    }

    /**
     * Ritorna l'utente se esso c'è
     * @param id: id dell'utente
     * @return ritorna l'utente
     * @throws ExceptionNoUser: se l'utente non esiste
     */
    public User getUserById(int id) throws ExceptionNoUser{
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();   
        }
        throw new ExceptionNoUser();
    }
}