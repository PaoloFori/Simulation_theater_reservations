package com.prenotazioni.spettacolo.Services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.prenotazioni.spettacolo.Entity.Posto;
import com.prenotazioni.spettacolo.Entity.Sala;
import com.prenotazioni.spettacolo.Entity.Spettacolo;
import com.prenotazioni.spettacolo.Exception.ExceptionNoPosto;
import com.prenotazioni.spettacolo.Exception.ExceptionNoSala;
import com.prenotazioni.spettacolo.Exception.ExceptionNoSpettacolo;
import com.prenotazioni.spettacolo.Repository.SpettacoloRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpettacoloServiceCommand {

    @Autowired
    SpettacoloRepository spettacoloRepository;

    /**
     * Elimina tutto dal DB specificando il nome dello show
     * @param spettacolo: nome dello show
     * @return una stringa che indica se tutto è andato bene o meno
     * @throws ExceptionNoSpettacolo: se non c'è lo spettacolo
     */
    public String deleteEverythingByShow(String spettacolo) throws ExceptionNoSpettacolo{
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(spettacolo);
        if(spett.isPresent()){
            Spettacolo s = spett.get();
            spettacoloRepository.delete(s);
            return "Show: "+spettacolo+ " deleted correctly";
        }else{
            throw new ExceptionNoSpettacolo();
        }
    }

    /**
     * Elimina una sala dato il numero di sala e lo show
     * @param spettacolo: nome dello show
     * @param numeroSala: numero di sala dello show
     * @return una stringa che indica se tutto è andato bene o meno
     * @throws ExceptionNoSpettacolo: se non esiste lo spettacolo
     * @throws ExceptionNoSala: se non esiste la sala
     */
    public String deleteSalaByNumeroSalaAndShow(String spettacolo, int numeroSala) throws ExceptionNoSpettacolo, ExceptionNoSala{
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacoloAndSala_NumeroSala(spettacolo, numeroSala);
        if(spett.isPresent()){
            Spettacolo s = spett.get();
            List<Sala> sale1 = s.getSala();
            List<Sala> sale = new ArrayList<>();
            for(int i = 0; i < sale1.size(); i++){
                Sala sala = sale1.get(i);
                if((sala.getNumeroSala() == numeroSala) == false){
                    sale.add(sala);
                }
            }
            Spettacolo s1 = new Spettacolo(spettacolo, sale, s.getCosto());
            spettacoloRepository.save(s1);
            return  "Deleted correctly\nSala: "+ numeroSala + " the show: " + spettacolo;
        }else{
            spett = spettacoloRepository.findBySpettacolo(spettacolo);
            if(spett.isPresent()){
                throw new ExceptionNoSala();
            }else{
                throw new ExceptionNoSpettacolo();
            }
        }
    }

    /**
     * Inserisco un nuovo spettacolo
     * @param spettacolo: spettacolo da inserire
     * @return una stringa che indica se tutto è andato bene o meno
     */
    public String insertNewSpettacoloAndSala(Spettacolo spettacolo){
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(spettacolo.getSpettacolo());
        if(spett.isPresent()){
            return "Alredy present in DB show named: " + spettacolo.getSpettacolo();
        }
        for(Spettacolo spettacol : spettacoloRepository.findAll()){
            for(Sala sal1 : spettacol.getSala()){
                for(Sala sal2 : spettacolo.getSala()){
                    if(sal1.getNumeroSala() == sal2.getNumeroSala()){
                        return "Sale already presten in DB";
                    }
                }
            }
        }
        spettacoloRepository.insert(spettacolo);
        return "New Show added correctly";
    }

    /**
     * Inserisce un nuovo spettacolo con aggiungendo sale già presenti nel DB
     * @param show: titolo dello show
     * @param cost: costo dello spettacolo
     * @param numeroSale: lista dei numeri delle sale da aggiungere
     * @return una stringa che indica se tutto è andato bene o meno
     */
    public String insertNewShowWithExistsSala(String show, int cost, List<Integer> numeroSale){
    	Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(show);
        if(spett.isPresent()){
            return "Show with name: " + show + " already exists";
        }
        Spettacolo sp = new Spettacolo(show, cost);
        
        List<String> numeroSaleString = new ArrayList<>();
        for(int i : numeroSale) {
        	String e = ""+ i;
        	numeroSaleString.add(e);
        }
        
        Iterator<String> iter = numeroSaleString.iterator();
        
        //vado in cerca delle altre sale su tutti gli altri show
        while(iter.hasNext()){
        	String numeroSal = iter.next();
            for(Spettacolo s : spettacoloRepository.findAll()){
            	boolean flag = false;
                for(Sala sala : s.getSala()){
                	String numeroSal1 = sala.getNumeroSala() + "";
                    if(numeroSal.equals(numeroSal1)){
                    	sp.addSala(sala);
                    	iter.remove();
                    	flag = true;
                    	break;
                    }
                }
                if(flag) {
                	break;
                }
            }
        }
        
        for(Sala sala1 : sp.getSala()) {
        	for(Posto posto : sala1.getPosto()) {
        		posto.setFree(true);
        	}
        }    
        
        spettacoloRepository.insert(sp);
        
        //guardo le sale non presenti nel db
        String saleNotPresent = "Sale not added, beacuse not exists in DB: ";
        if(numeroSaleString.size() > 0) {
        	for(String i : numeroSaleString) {
        		saleNotPresent = saleNotPresent + " " + i;
        	}
        	return "New Show added correctly" + "\n" + saleNotPresent;
        }

        return "New Show added correctly";
    }

    /**
     * Inserisce una nuova sala dove devi dire tutti i parametri e indicare anche lo spettacolo
     * @param sala: indica tutte le caratteristiche della Sala
     * @param titolo: indica il titolo dello spettacolo che avrà una sala in più
     * @return: ritorna una stringa e dice se è andata bene o no l'aggiunta
     * @throws ExceptionNoSpettacolo: se non c'è lo spettacolo
     */
    public String insertSalaBySpettacolo(String titolo, Sala sala) throws ExceptionNoSpettacolo{
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(titolo);
        if(spett.isPresent()){
            Spettacolo s = spett.get();
            List<Sala> sale = s.getSala();
            for(Spettacolo show : spettacoloRepository.findAll()) {
                for(Sala sal : show.getSala()){
                    if(sala.getNumeroSala() == sal.getNumeroSala()){
                        return "Already exists Sala with numeroSala: " + sala.getNumeroSala();
                    }
                }
            }
            sale.add(sala);
            s.setSala(sale);
            spettacoloRepository.save(s);
            return "New Sala added correctly for the show " + titolo;
        }else{
            throw new ExceptionNoSpettacolo();
        }
    }

    /**
     * Inserisce una sala già esistente nel db
     * @param titolo: titolo dello spettacolo
     * @param numeroSale: lista dei numeri delle sale da aggiungere
     * @return una stringa che indica se tutto è andato bene o meno
     * @throws ExceptionNoSpettacolo: se lo spettacolo non esiste
     */
    public String insertAlreadyExistsSalaByShow(String titolo, List<Integer> numeroSale) throws ExceptionNoSpettacolo{
    	Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(titolo);
        if(spett.isPresent()){
            Spettacolo s = spett.get();
            String saleAlreadyPresent = "Sale not added beacuse already present: ";
            
            //creo una lista di stringhe
            List<String> numeroSaleString = new ArrayList<>();
            for(int i : numeroSale) {
            	String e = i +"";
            	numeroSaleString.add(e);
            }
            
            //rimuovo dalla lista le sale già presenti nello show
            Iterator<String> iter = numeroSaleString.iterator();
            while(iter.hasNext()){
            	String numeroSal = iter.next();
                for(Sala sala : s.getSala()){
                	String numeroSal1 = sala.getNumeroSala() + "";
                    if(numeroSal.equals(numeroSal1)){
                        saleAlreadyPresent = saleAlreadyPresent + numeroSal1 + " ";
                        iter.remove();
                        break;
                    }
                }
            }
            
            //vado in cerca delle altre sale su tutti gli altri show, setto i posti e free = true e gli aggiungo
            iter = numeroSaleString.iterator();
            while(iter.hasNext()){
            	String numeroSal = iter.next();
                for(Spettacolo show : spettacoloRepository.findAll()){
                	boolean flag = false;
                    for(Sala sala : show.getSala()){
                    	String numeroSal1 = sala.getNumeroSala() + "";
                        if(numeroSal.equals(numeroSal1)){
                        	Sala sal = new Sala(sala.getNumeroSala(), sala.getPosto());
                        	for(Posto post : sal.getPosto()) {
                        		post.setFree(true);
                        	}
                        	s.addSala(sal);
                        	iter.remove();
                        	flag = true;
                        	break;
                        }
                    }
                    if(flag) {
                    	break;
                    }
                }
            }

            spettacoloRepository.save(s);
            String saleNotAdded = "Sala not added beacuse don't exist: ";
            for(int i = 0; i < numeroSaleString.size(); i++){
                saleNotAdded = saleNotAdded + numeroSaleString.get(i) + " ";
            }
            if(((saleAlreadyPresent.equals("Sale not added beacuse already present: ")) == false) && ((saleNotAdded.equals("Sala not added beacuse don't exist: ")) == false)){
            	return s.toString() + "\n" + saleNotAdded + "\n" + saleAlreadyPresent;
            }
            if((saleAlreadyPresent.equals("Sale not added beacuse already present: ")) == false) {
            	return s.toString() + "\n" + saleAlreadyPresent;
            }
            if((saleNotAdded.equals("Sala not added beacuse don't exist: ")) == false) {
            	return s.toString() + "\n" + saleNotAdded;
            }
            
            return s.toString();
        }else{
            throw new ExceptionNoSpettacolo();
        }
    }

    /**
     * Permette di settare il posto di una sala per uno spettacolo
     * @param spettacolo: titolo dello spettacolo
     * @param numeroSala: numero della sala dove si svolge lo spettacolo
     * @param posto: posto da voler settare
     * @return uno Spettacolo con i parametri settati giusti
     * @throws ExceptionNoSpettacolo: non esiste lo spettacolo
     * @throws ExceptionNoSala: non esiste la sala
     * @throws ExceptionNoPosto: non esiste il posto
     */
    public Spettacolo setPostoByTitoloAnNumeroSalaAndPosto(String spettacolo, int numeroSala, Posto posto) throws ExceptionNoSpettacolo, ExceptionNoSala, ExceptionNoPosto{
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(spettacolo);
        if(spett.isPresent()){
            boolean flag = false;
            boolean flag2 = false;
            Spettacolo s = spett.get();
            List<Sala> sale = s.getSala();
            Sala sala = new Sala();
            for(int i = 0; i < sale.size(); i++){
                sala = sale.get(i);
                if(sala.getNumeroSala() == numeroSala){
                    flag = true;
                    List<Posto> posti = sala.getPosto();
                    for(int j = 0; j <posti.size(); j ++){
                        Posto posto1 = posti.get(j);
                        if((posto1.getColonna()==posto.getColonna()) && posto1.getRiga().equals(posto.getRiga())){
                            posto1.setFree(posto.getFree());
                            flag2 = true;
                        }
                    }
                }
                if(flag){
                    break;
                }
            }
            if(flag == false){
                throw new ExceptionNoSala();
            }else if(flag2 == false){
                throw new ExceptionNoPosto();
            }else{
                spettacoloRepository.save(s);
                return s;
            }
        }else{
            throw new ExceptionNoSpettacolo();
        }
    }
}
