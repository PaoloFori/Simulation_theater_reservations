package com.prenotazioni.utente.Repository;

import java.util.List;

import com.prenotazioni.utente.Entity.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Integer>{
   public List<User> findByName(String name); 
   public List<User> findBySurname(String surname);
}