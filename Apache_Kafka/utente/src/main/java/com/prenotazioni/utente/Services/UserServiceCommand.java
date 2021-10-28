package com.prenotazioni.utente.Services;

import java.util.Optional;

import com.prenotazioni.utente.Entity.User;
import com.prenotazioni.utente.Exception.ExceptionNoEnoughMoney;
import com.prenotazioni.utente.Exception.ExceptionNoUser;
import com.prenotazioni.utente.Repository.UserRepository;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceCommand {
    @Autowired
    UserRepository userRepository;

    @Autowired
    KafkaTemplate<String, String> template;

    /**
     * pubblica un messaggio sul topic User
     * @param user: utente da passare
     */
    public void publishMessage(User user){
        ProducerRecord<String, String> message = new ProducerRecord<String,String>("User", user.toString());
        template.send(message);
    }

    /**
     * Rimuovi un utente dall'id
     * @param id: id dell'utente
     * @return ritorna una stringa per dire se è andato tutto bene
     * @throws ExceptionNoUser: se non esiste quell'utente
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
     * @param user: utente da inserire
     * @return ritorna una stringa per dire se è andato tutto bene
     */
    public String insert(User user) {
        userRepository.insert(user); 
        return "User add correctly!";
    }

    /**
     * Aggiorna utenti esistenti
     * @param id: id dell'utente
     * @param name: nome dell'utente
     * @param surname: cognome dell'utente
     * @param cash: soldi dell'utente
     * @return ritorna una stringa per dire se è andato tutto bene
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
            if((cash == -1) == false) {
            	user.setCash(cash);
            }
            userRepository.save(user);
            return "User upset correctly!";
        }else{
            throw new ExceptionNoUser("No User with this id");
        }
    }

    /**
     * Rimuove soldi dall'user passato come ID
     * @param id: id dell'utente
     * @param soldi: soldi da rimuovre
     * @return ritorna una stringa per dire se è andato tutto bene
     * @throws ExceptionNoEnoughMoney: se non ha abbastanza soldi
     * @throws ExceptionNoUser: se l'utente non esiste
     */
    public String removeCash(int id, int soldi) throws ExceptionNoEnoughMoney, ExceptionNoUser{
        Optional<User> userr = userRepository.findById(id);
        if(userr.isPresent()){
            User user = userr.get();
            int money = user.getCash();
            if((money-soldi) < 0){ //non ha a sufficienza i soldi
                throw new ExceptionNoEnoughMoney("No enougth money!!");
            }
            soldi = money-soldi;
            user.setCash(soldi);
            userRepository.save(user);
            return "User has enogth money. The now money are: " + soldi;
        }else{
            throw new ExceptionNoUser("No user in DB");
        }
    }

    /**
     * Aggiunge soldi all'User passato come id
     * @param id: id dell'utente
     * @param money: soldi da aggiungere
     * @return ritorna una stringa per dire se è andato tutto bene
     * @throws ExceptionNoUser: se non esiste l'utente
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
            throw new ExceptionNoUser("No user in DB");
        }
    }
}