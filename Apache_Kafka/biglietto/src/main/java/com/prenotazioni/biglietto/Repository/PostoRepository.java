package com.prenotazioni.biglietto.Repository;

import java.util.Optional;

import com.prenotazioni.biglietto.Entity.Posto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@Repository
@EnableJpaRepositories
public interface PostoRepository extends JpaRepository<Posto, String>{
    public Optional<Posto> findByRigaAndColonna(String riga, int colonna);
}