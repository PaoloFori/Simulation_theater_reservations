package com.prenotazioni.biglietto.Repository;

import java.util.List;
import java.util.Optional;

import com.prenotazioni.biglietto.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
    public Optional<User> findById(int id);
    public List<User> findByName(String name);
    public List<User> findBySurname(String surname);
}