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

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpettacoloServiceCommand {
    @Autowired
    SpettacoloRepository spettacoloRepository;

    @Autowired
    KafkaTemplate<String, String> producer;

    //serve per pubblicare i messaggi nel topic Spettacolo
    public void publishMessage(Spettacolo spettacolo){
        producer.send(new ProducerRecord<String,String>("Spettacolo", spettacolo.toString()));
    }

    /**
     * Elimina tutto dal DB specificando il nome dello show
     * @param spettacolo: nome dello show
     * @return stringa che indica se tutto andato bene
     * @throws ExceptionNoSpettacolo: se non è presente lo show
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
     * @param spettacolo: titolo dello show
     * @param numeroSala: numero della sala da eliminare
     * @return stringa che indica se tutto andato bene
     * @throws ExceptionNoSpettacolo: se non c'è lo show
     * @throws ExceptionNoSala: se non c'è la sala
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
            Spettacolo s1 = new Spettacolo(spettacolo, sale, s.getCosto(), "Upset");
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
     * @return stringa che indice se lo show è stato aggiunto o meno e il perchè
     */
    public String insertNewSpettacolo(Spettacolo spettacolo){
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(spettacolo.getSpettacolo());
        if(spett.isPresent()){
            return "Show: "+spettacolo.getSpettacolo()+" alredy present";
        }
        List<Spettacolo> listOfSpettacolo = spettacoloRepository.findAll();
        for(Spettacolo show : listOfSpettacolo){
            for(Sala sala : show.getSala()){
                for(Sala sala1 : spettacolo.getSala()){
                    if(sala.getNumeroSala() == sala1.getNumeroSala()){
                        return "Sala: " + sala.getNumeroSala() + " already exists";
                    }
                }
            }
        }
        spettacoloRepository.insert(spettacolo);
        publishMessage(spettacolo);
        return "New Show added correctly";
    }

    /**
     * Inserisco uno show conoscendo le sale dove verrà proiettato
     * @param spettacolo: titolo dello show
     * @param costo: costo del biglietto per la visione
     * @param numeroSale: lista delle sale dove verrà proiettato
     * @return una stringa che indica lo show e le sale aggiunte o non aggiunte
     */
    public String insertNewSpettacoloWithSalaAlreadyExist(String spettacolo, int costo, List<Integer> numeroSale){
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(spettacolo);
        if(spett.isPresent()){
            return "Show with name: " + spettacolo + " already exists";
        }
        Spettacolo sp = new Spettacolo(spettacolo, costo, "Add New");
        
        List<String> numeroSaleString = new ArrayList<>();
        for(int i : numeroSale) {
        	String e = ""+ i;
        	numeroSaleString.add(e);
        }
        
        Iterator<String> iter = numeroSaleString.iterator();
        
        //vado in cerca delle altre sale su tutti gli altri show
        while(iter.hasNext()){
        	String numeroSal = iter.next();
            for(Spettacolo show : spettacoloRepository.findAll()){
            	boolean flag = false;
                for(Sala sala : show.getSala()){
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
        publishMessage(sp);
        
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
     * Aggiunge ad uno spettacolo una sala già esistente
     * @param titolo: titolo dello show
     * @param numeroSala: numero della sala
     * @return stringa che indica lo show e se sono state aggiunte tutte le sale date o meno
     * @throws ExceptionNoSpettacolo: se non esiste lo show
     */
    public String insertExistSalaBySpettacolo(String titolo, List<Integer> numeroSale) throws ExceptionNoSpettacolo{
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
     * Inserisce una nuova sala dove devi dire tutti i parametri e indicare anche lo spettacolo
     * @param sala: indica tutte le caratteristiche della Sala
     * @param titolo: indica il titolo dello spettacolo che avrà una sala in più
     * @return: ritorna una stringa e dice se è andata bene o no l'aggiunta
     * @throws ExceptionNoSpettacolo: se non esiste lo show
     */
    public String insertSalaBySpettacolo(String titolo, Sala sala) throws ExceptionNoSpettacolo{
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(titolo);
        if(spett.isPresent()){
            Spettacolo s = spett.get();
            List<Sala> sale = s.getSala();
            //controllo su tutte le sale per vedere se ne esiste una con lo stesso numero sala
            for(Spettacolo sp : spettacoloRepository.findAll()){
                for(Sala salaOld : sp.getSala()){
                    if(salaOld.getNumeroSala() == sala.getNumeroSala()){
                        return "Sala not added, because already exist in DB sala with numeroSala: " + sala.getNumeroSala();
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
     * Permette di settare il posto di una sala per uno spettacolo
     * @param spettacolo: titolo dello show
     * @param numeroSala: numero della sala
     * @param posto: posto
     * @return uno spettacolo con il posto settato correttamente
     * @throws ExceptionNoSpettacolo: se non esiste lo show
     * @throws ExceptionNoSala: se non esiste la sala
     * @throws ExceptionNoPosto: se non esiste il posto
     */
    public Spettacolo setPostoByTitoloAnNumeroSalaAndPosto(String spettacolo, int numeroSala, Posto posto) throws ExceptionNoSpettacolo, ExceptionNoSala, ExceptionNoPosto{
        Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(spettacolo);
        if(spett.isPresent()){
            boolean flag = false;
            Spettacolo s = spett.get();
            boolean flag2 = false;
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
