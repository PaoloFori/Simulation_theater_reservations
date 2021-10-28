package com.prenotazioni.spettacolo.Repository;

import java.util.Optional;

import com.prenotazioni.spettacolo.Entity.Spettacolo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpettacoloRepository extends MongoRepository<Spettacolo, String>{

    /**
     * @param spettacolo: indica il nome da ricercare
     * @return spettacolo oppure Null
     */
    public Optional<Spettacolo> findBySpettacolo(String spettacolo);

    /**
     * I paramentri indicano il nome dello spettacolo e della sala da ricercare
     * @param spettacolo
     * @param numeroSala
     * @return spettacolo oppure Null
     */
    public Optional<Spettacolo> findBySpettacoloAndSala_NumeroSala(String spettacolo, int numeroSala);
}