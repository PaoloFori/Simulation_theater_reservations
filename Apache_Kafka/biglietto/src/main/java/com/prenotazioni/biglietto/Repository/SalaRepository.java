package com.prenotazioni.biglietto.Repository;

import com.prenotazioni.biglietto.Entity.Sala;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@Repository
@EnableJpaRepositories
public interface SalaRepository extends JpaRepository<Sala, Integer>{

}