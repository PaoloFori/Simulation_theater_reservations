package com.prenotazioni.biglietto.Repository;

import java.util.Optional;

import com.prenotazioni.biglietto.Entity.Spettacolo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@Repository
@EnableJpaRepositories
public interface SpettacoloRepository extends JpaRepository<Spettacolo, String> {
    public Optional<Spettacolo> findBySpettacolo(String spettacolo);
    public Optional<Spettacolo> findBySpettacoloAndSala_NumeroSala(String spettacolo, int numeroSala);
}