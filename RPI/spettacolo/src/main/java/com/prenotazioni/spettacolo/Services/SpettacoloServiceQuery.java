package com.prenotazioni.spettacolo.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.prenotazioni.spettacolo.Entity.Posto;
import com.prenotazioni.spettacolo.Entity.Sala;
import com.prenotazioni.spettacolo.Entity.Spettacolo;
import com.prenotazioni.spettacolo.Exception.ExceptionNoSala;
import com.prenotazioni.spettacolo.Exception.ExceptionNoSpettacolo;
import com.prenotazioni.spettacolo.Repository.SpettacoloRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpettacoloServiceQuery {

    @Autowired
    SpettacoloRepository spettacoloRepository;

    /**
     * Ritorna tutti gli spettacoli
     * @return lista di tutti gli spettacoli
     */
    public List<Spettacolo> getAll() {
        return spettacoloRepository.findAll();
    }

    /**
     * Spettacolo
     * @param spettacolo: titolo dello spettacolo da ricercare
     * @return un determinato spettacolo
     * @throws ExceptionNoSpettacolo: se non esiste lo spettacolo
     */
    public Spettacolo getSpettacolo(String spettacolo) throws ExceptionNoSpettacolo{
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(spettacolo);
        if(spett.isPresent()){
            return spett.get();
        }else{
            throw new ExceptionNoSpettacolo();
        }
    }

    /**
     * Ritorna uno spettacolo con tutti i parametri per la stanza indicata
     * @param spettacolo: titolo dello spettacolo
     * @param numeroSala: sala dello spettacolo
     * @return Spettacolo e i parametri 
     * @throws ExceptionNoSpettacolo: se non esiste lo spettacolo
     * @throws ExceptionNoSala: se non esiste la sala
     */
    public Spettacolo getSpettacoloBySpettacoloAndNumeroSala(String spettacolo, int numeroSala) throws ExceptionNoSpettacolo, ExceptionNoSala{
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(spettacolo);
        if(spett.isPresent()){
            Spettacolo s1 = spett.get();
            Spettacolo spettacol = new Spettacolo();
            int i;
            boolean flag = false;
            List<Sala> sale = s1.getSala();
            for(i = 0; i < sale.size(); i++){
                Sala sala = sale.get(i);
                if(sala.getNumeroSala() == numeroSala){
                    spettacol.setCosto(s1.getCosto());
                    spettacol.setSpettacolo(spettacolo);
                    spettacol.setSala(Arrays.asList(sala));
                    flag = true;
                    break;
                }
            }
            if(flag){
                return spettacol;
            }else{
                throw new ExceptionNoSala();
            }
        }else{
            throw new ExceptionNoSpettacolo();
        }
    }

    /**
     * Ritorna il costo per lo spettacolo indicato
     * @param spettacolo: titolo dello spettacolo
     * @return il costo dello spettacolo
     * @throws ExceptionNoSpettacolo: se non esiste lo spettacolo
     */
    public int getCostoBySpettacolo(String spettacolo) throws ExceptionNoSpettacolo{
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(spettacolo);
        if(spett.isPresent()){
            Spettacolo s = spett.get();
            return s.getCosto();
        }else{
            throw new ExceptionNoSpettacolo();
        }
    }

    /**
     * Ritorna una lista con tutte le sale per uno spettacolo
     * @param titolo: titolo dello spettacolo
     * @return lista delle sale di quello spettacolo
     * @throws ExceptionNoSpettacolo: se non esiste lo spettacolo
     */
    public List<Sala> getSaleBySpettacolo(String titolo) throws ExceptionNoSpettacolo{
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(titolo);
        if(spett.isPresent()){
            Spettacolo s = spett.get();
            return s.getSala();
        }else{
            throw new ExceptionNoSpettacolo();
        }
    }

    /**
     * Ritorna tutti i posti liberi di tutte le sale dato lo spettacolo
     * @param spettacolo: titolo dello spettacolo
     * @return spettacolo con solo i posti che hanno free = true
     * @throws ExceptionNoSpettacolo: se non esiste lo spettacolo
     */
    public Spettacolo getAllFreePostoPerAllSalaByShow(String spettacolo) throws ExceptionNoSpettacolo{
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(spettacolo);
        if(spett.isPresent()){
            Spettacolo s = spett.get();
            Spettacolo s1 = new Spettacolo();
            List<Sala> sale = s.getSala();
            List<Sala> sale1 = new ArrayList<>();
            Sala sala;
            Sala sala1;
            List<Posto> posti;
            List<Posto> posti1;
            Posto posto;
            for(int i = 0; i< sale.size(); i++){
                posti1 = new ArrayList<>();
                sala1 = new Sala();
                sala = sale.get(i);
                posti = sala.getPosto();
                sala1.setNumeroSala(sala.getNumeroSala());
                for(int j = 0; j < posti.size(); j++){
                    posto = posti.get(j);
                    if(posto.getFree()){
                        posti1.add(posto);
                    }
                }
                sala1.setPosto(posti1);
                sale1.add(sala1);
            }
            s1.setCosto(s.getCosto());
            s1.setSala(sale1);
            s1.setSpettacolo(spettacolo);
            return s1;
        }else{
            throw new ExceptionNoSpettacolo();
        }
    }

    /**
     * Ritorna tutti i posti liberi per lo spettacolo e sala speficifati
     * @param spettacolo: titolo dello spettacolo
     * @param numeroSala: numero della sala
     * @return spettacolo con solo i posti che hanno free = true e che appartengono alla sala richiesta
     * @throws ExceptionNoSpettacolo: se non esiste lo spettacolo
     * @throws ExceptionNoSala: se non esiste la sala
     */
    public Spettacolo getAllFreePostoBySpettacoloAndNumeroSala(String spettacolo, int numeroSala)throws ExceptionNoSpettacolo, ExceptionNoSala{
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(spettacolo);
        if(spett.isPresent()){
            Spettacolo s = spett.get();
            Spettacolo s1 = new Spettacolo();
            List<Sala> sale = s.getSala();
            List<Posto> posti;
            List<Posto> posto1 = new ArrayList<>();
            Sala sala = new Sala();
            Sala sala1 = new Sala();
            Posto posto;
            boolean flag = false;
            for(int i = 0; i < sale.size(); i++){
                sala = sale.get(i);
                if(numeroSala == sala.getNumeroSala()){
                    flag = true;
                    break;
                }
            }
            if(flag){
                posti = sala.getPosto();
                for(int j = 0; j < posti.size(); j++){
                    posto = posti.get(j);
                    if(posto.getFree()){
                        posto1.add(posto);
                    }
                }
                sala1.setNumeroSala(numeroSala);
                sala1.setPosto(posto1);
                s1.setCosto(s.getCosto());
                s1.setSpettacolo(spettacolo);
                s1.setSala(Arrays.asList(sala1));
            }
            if(flag == false){
                throw new ExceptionNoSala();
            }
            return s1;
        }else{
            throw new ExceptionNoSpettacolo();
        }
    }
}