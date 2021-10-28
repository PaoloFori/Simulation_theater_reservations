package com.prenotazioni.utente.Services;

import java.util.Optional;

import com.prenotazioni.utente.Entity.User;
import com.prenotazioni.utente.Exception.ExceptionNoEnoughMoney;
import com.prenotazioni.utente.Exception.ExceptionNoUser;
import com.prenotazioni.utente.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceCommand {

    @Autowired
    UserRepository userRepository;
    
    /**
     * Rimuovi un utente dall'id
     * @param id: id dell'utente
     * @return una stringa che indica se tutto andanto bene o no
     * @throws ExceptionNoUser: se non c'è quel id
     */
    public String deleteById(int id) throws ExceptionNoUser{
        Optional<User> userr = userRepository.findById(id);
        if(userr.isPresent()){
            User user = userr.get();
            userRepository.delete(user);
            return "User delete correctly";
        }else{
            throw new ExceptionNoUser();
        }
    }

    /**
     * Inserisce nuovi utenti
     * 
     * @param id: id del nuovo utente
     * @param name: nome del nuovo utente
     * @param surname: cognome del nuovo utente
     * @param money: soldi del nuovo utente
     * @return una stringa che indica se tutto andanto bene o no
     */
    public String insert(int id, String name, String surname, int money) {
        User user = new User(id, name, surname, money);
        userRepository.insert(user);
        return "User add correctly!";
    }

    /**
     * Aggiorna utenti esistenti, tutti i parametri tranne l'id sono facoltativi mettento "", se stringe, oppure -1, se interi
     * 
     * @param id: ide dell'utente
     * @param name: nuovo nome da dare a quel utente
     * @param surname: nuovo cognome da dare a quell utente
     * @param cash: nuovi soldi per l'utente
     * @return una stringa che indica se tutto andanto bene o no
     */
    public String upset(int id, String name, String surname, int cash) throws ExceptionNoUser{
        Optional<User> userr = userRepository.findById(id);
        if(userr.isPresent()==true){
            User user = userr.get();
            if(name.equals("")==false){
                user.setName(name);
            }
            if(surname.equals("")==false){
                user.setSurname(surname);
            }
            if((cash == -1) == false){
                user.setCash(cash);
            }
            userRepository.save(user);
            return "User upset correctly!";
        }else{
            throw new ExceptionNoUser();
        }
    }

    /**
     * Rimuove soldi dall'user passato come ID
     * @param id: id dell'utente
     * @param soldi: soldi da dover rimuovere all'utente
     * @return una stringa che indica se tutto andanto bene o no
     * @throws ExceptionNoEnoughMoney: se l'utente non ha abbastanza soldi
     * @throws ExceptionNoUser: se non esiste l'utente con quell'id
     */
    public String removeCash(int id, int soldi) throws ExceptionNoEnoughMoney, ExceptionNoUser{
        Optional<User> userr = userRepository.findById(id);
        if(userr.isPresent()){
            User user = userr.get();
            int money = user.getCash();
            if((money-soldi) < 0){ //non ha a sufficienza i soldi
                throw new ExceptionNoEnoughMoney();
            }
            soldi = money-soldi;
            user.setCash(soldi);
            userRepository.save(user);
            return "User has enogth money. The now money are: " + soldi;
        }else{
            throw new ExceptionNoUser();
        }
    }

    /**
     * Aggiunge soldi ad un determinato utente
     * @param id: id dell'utente
     * @param money: soldi da aggiungere a quell'utente
     * @return una stringa che indica se tutto andanto bene o no
     * @throws ExceptionNoUser: se non esiste l'utente con quell'id
     */
    public String addCash(int id, int money) throws ExceptionNoUser{
        Optional<User> userr = userRepository.findById(id);
        if(userr.isPresent()){
            User user = userr.get();
            int soldi = user.getCash();
            soldi = soldi + money;
            user.setCash(soldi);
            userRepository.save(user);
            return "User money now are: " + soldi;
        }else{
            throw new ExceptionNoUser();
        }
    }
}