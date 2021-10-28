package com.prenotazioni.biglietto.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.prenotazioni.biglietto.Entity.Posto;
import com.prenotazioni.biglietto.Entity.Sala;
import com.prenotazioni.biglietto.Entity.Spettacolo;
import com.prenotazioni.biglietto.Exceptions.ExceptionNoSala;
import com.prenotazioni.biglietto.Exceptions.ExceptionNoSpettacolo;
import com.prenotazioni.biglietto.Repository.PostoRepository;
import com.prenotazioni.biglietto.Repository.SalaRepository;
import com.prenotazioni.biglietto.Repository.SpettacoloRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpettacoloServiceQuery {

    @Autowired
	SpettacoloRepository spettacoloRepository;

    @Autowired
    SalaRepository salaRepository;

    @Autowired
    PostoRepository postoRepository;

    /**
     * Ritorna tutti gli spettacoli
     * @return
     */
    public List<String> getAll() {
        List<String> listOfSpettacolo = new ArrayList<>();
        for(Spettacolo spettacolo : spettacoloRepository.findAll()){
            listOfSpettacolo.add(spettacolo.toString());
        }
        return listOfSpettacolo;
    }

    /**
     * Spettacolo
     * @param spettacolo: titolo dello spettacolo da ricercare
     * @return
     * @throws ExceptionNoSpettacolo
     */
    public String getSpettacolo(String spettacolo) throws ExceptionNoSpettacolo{
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(spettacolo);
        if(spett.isPresent()){
            return spett.get().toString();
        }else{
            throw new ExceptionNoSpettacolo();
        }
    }

    /**
     * Ritorna uno spettacolo con tutti i parametri per la stanza indicata
     * @param spettacolo
     * @param numeroSala
     * @return Spettacolo e i parametri o spettacolo nullo
     * @throws ExceptionNoSpettacolo
     */
    public String getSpettacoloBySpettacoloAndNumeroSala(String spettacolo, int numeroSala) throws ExceptionNoSpettacolo, ExceptionNoSala{
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacoloAndSala_NumeroSala(spettacolo, numeroSala);
        if(spett.isPresent()){
            Spettacolo s1 = spett.get();
            Spettacolo s2 = new Spettacolo();
            s2.setCosto(s1.getCosto());
            s2.setSpettacolo(s1.getSpettacolo());
            for(Sala sala : s1.getSala()) {
            	if(sala.getNumeroSala() == numeroSala) {
            		s2.setSala(Arrays.asList(sala));
            	}
            }
            return s2.toString();
        }else{
            Optional<Spettacolo> spettac = spettacoloRepository.findBySpettacolo(spettacolo);
            if(spettac.isPresent()){
                throw new ExceptionNoSala();
            }
            throw new ExceptionNoSpettacolo();
        }
    }

    /**
     * Ritorna il costo per lo spettacolo indicato
     * @param spettacolo
     * @return
     * @throws ExceptionNoSpettacolo
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
     * @param titolo
     * @return
     * @throws ExceptionNoSpettacolo
     */
    public List<String> getSaleBySpettacolo(String titolo) throws ExceptionNoSpettacolo{
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(titolo);
        if(spett.isPresent()){
            Spettacolo s = spett.get();
            List<String> listOfSala = new ArrayList<>();
            for(Sala sala : s.getSala()){
                listOfSala.add(sala.toString());
            }
            return listOfSala;
        }else{
            throw new ExceptionNoSpettacolo();
        }
    }

    /**
     * Ritorna tutti i posti liberi di tutte le sale dato lo spettacolo
     * @param spettacolo: titolo dello spettacolo
     * @return
     * @throws ExceptionNoSpettacolo
     */
    public String getAllFreePostoPerAllSalaByShow(String spettacolo) throws ExceptionNoSpettacolo{
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
            return s1.toString();
        }else{
            throw new ExceptionNoSpettacolo();
        }
    }

    /**
     * Ritorna tutti i posti liberi per lo spettacolo e sala speficifati
     * @param spettacolo
     * @param numeroSala
     * @return
     * @throws ExceptionNoSpettacolo
     */
    public String getAllFreePostoBySpettacoloAndNumeroSala(String spettacolo, int numeroSala)throws ExceptionNoSpettacolo, ExceptionNoSala{
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
            return s1.toString();
        }else{
            throw new ExceptionNoSpettacolo();
        }
    }
}