package com.prenotazioni.apigateway.Services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.prenotazioni.apigateway.Entity.Posto;
import com.prenotazioni.apigateway.Entity.Sala;
import com.prenotazioni.apigateway.Entity.Spettacolo;
import com.prenotazioni.apigateway.Entity.User;
import com.prenotazioni.apigateway.Exception.ExceptionNoEnoughMoney;
import com.prenotazioni.apigateway.Exception.ExceptionNoFreePosto;
import com.prenotazioni.apigateway.Exception.ExceptionNoPosto;
import com.prenotazioni.apigateway.Exception.ExceptionNoSala;
import com.prenotazioni.apigateway.Exception.ExceptionNoSpettacolo;
import com.prenotazioni.apigateway.Exception.ExceptionNoUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiGatewayServiceQuery {
    
    @Autowired
    RestTemplate restTemplate;

    /**
     * Ritorna tutti gli show nel db
     * @return lista di stringhe con tutti gli show
     */
    public List<String> getAllShows(){
        List<Spettacolo> shows =  Arrays.asList(restTemplate.getForObject("http://localhost:8085/Teatro/shows", Spettacolo[].class));
        List<String> listOfString = new ArrayList<>();
        for(Spettacolo show : shows){
            listOfString.add(show.toString());
        }
        return listOfString;
    }

    /**
     * Ritorna il costo delle spettacolo
     * @param spettacolo: titolo dello show
     * @return una stringa che indica se tutto andato bene o no
     */
    public String getCostoBySpettacolo(String spettacolo){
        String uri = "http://localhost:8085/Teatro/show?show=" + spettacolo;
        return restTemplate.getForObject(uri, String.class);
    }

    /**
     * Ritorna lo spettacolo dato il titolo, li ritorna tutti o qulli di una determinaata stanza
     * @param titolo: titolo dello shoe
     * @param numeroSala: numero della sala
     * @return una stringa che indica se tutto andato bene o no
     * @throws ExceptionNoSpettacolo: se non esiste lo show
     * @throws ExceptionNoSala: se non esiste la sala
     */
    public String getShowByParam(String titolo, int numeroSala) throws ExceptionNoSpettacolo, ExceptionNoSala{
        if(numeroSala == -1){
            Spettacolo spettacolo = restTemplate.getForObject("http://localhost:8085/Teatro/"+titolo, Spettacolo.class);
            if(spettacolo == null){
                throw new ExceptionNoSpettacolo();
            }
            return spettacolo.toString();
        }else{
            Spettacolo show = restTemplate.getForObject("http://localhost:8085/Teatro/"+titolo+"/"+numeroSala, Spettacolo.class);
            if(show == null){
                Spettacolo spettacolo = restTemplate.getForObject("http://localhost:8085/Teatro/"+titolo, Spettacolo.class);
                if(spettacolo == null){
                    throw new ExceptionNoSpettacolo();
                }else{
                    throw new ExceptionNoSala();
                }
            }
            return show.toString();
        }
    }

    /**
     * Ritorna tutti i posti liberi di uno spettacolo di qualsiasi sala
     * @param spettacolo: titolo dello show
     * @return una stringa che indica se tutto andato bene o no
     * @throws ExceptionNoSpettacolo: se non esiste lo show
     */
    public String getAllFreePostoPerAllSalaByShow(String spettacolo) throws ExceptionNoSpettacolo{
        Spettacolo show = restTemplate.getForObject("http://localhost:8085/Teatro/"+spettacolo+"/freePosti", Spettacolo.class);
        if(show == null){
            throw new ExceptionNoSpettacolo();
        }
        return show.toString();
    }

    /**
     * Ritorna i posti liberi di uno spettacolo dati il nome dello show e il numero della sala
     * @param show: titolo dello show
     * @param numeroSala: numero della sala
     * @return una stringa che indica se tutto andato bene o no
     * @throws ExceptionNoSpettacolo: se non esiste lo show
     * @throws ExceptionNoSala: se non esiste la sala
     */
    public String getAllFreePostoByNumeroSalaAndShow(String show, int numeroSala) throws ExceptionNoSpettacolo, ExceptionNoSala{
        Spettacolo spettacolo = restTemplate.getForObject("http://localhost:8085/Teatro/"+show+"/"+numeroSala+"/freePosti", Spettacolo.class);
        if(spettacolo == null){
            Spettacolo show1 = restTemplate.getForObject("http://localhost:8085/Teatro/"+show+"/freePosti", Spettacolo.class);
            if(show1 == null){
                throw new ExceptionNoSpettacolo();
            }else{
                throw new ExceptionNoSala();
            }
        }
        return spettacolo.toString();
    }

    /**
     * Ritorna lista con tutti gli utenti
     * @return lista di stringhe con tutti gli utenti
     */
    public List<String> getAllUser(){
        String uri = "http://localhost:8081/users";
        List<User> users = Arrays.asList(restTemplate.getForObject(uri, User[].class));
        List<String> listOfString = new ArrayList<>();
        for(User user : users){
            listOfString.add(user.toString());
        }
        return listOfString;
    }

    /**
     * ritorna un utente specifico dato l'id
     * @param id: id dell'utente
     * @return una stringa che indica se tutto andato bene o no
     * @throws ExceptionNoUser: se non esiste l'utente
     */
    public String getUserById(int id) throws ExceptionNoUser{
        String uri = "http://localhost:8081/userId?id=" + id;
        User user = restTemplate.getForObject(uri, User.class);
        if(user == null){
            throw new ExceptionNoUser();
        }
        return user.toString();
    }

    /**
     * Ritonra una lista di tutti gli utenti trovati per quel nome o cognome o entrambi
     * @param name: nome dell'utente
     * @param surname: cognome dell'utene
     * @return lista di stringhe con gli utenti con stesso nome o stesso cognome o si astesso nome sia stesso cognome
     */
    public List<String> getUserByNameOrSurnameOrBoth(String name, String surname){
        List<User> users = new ArrayList<>();
        if(name.equals("") && !surname.equals("")){
            String uri = "http://localhost:8081/userSurname?surname=" + surname;
            users = Arrays.asList(restTemplate.getForObject(uri, User[].class));
        }else if(surname.equals("") && !name.equals("")){
            String uri = "http://localhost:8081/userName?name=" + name;
            users = Arrays.asList(restTemplate.getForObject(uri, User[].class));
        }else if(name.equals("") && surname.equals("")){
            return Arrays.asList("Put name or surname or both");
        }else if(!name.equals("") && !surname.equals("")){
            String uriName = "http://localhost:8081/userName?name=" + name;
            List<User> usersName =  Arrays.asList(restTemplate.getForObject(uriName, User[].class));
            String uriSurname = "http://localhost:8081/userSurname?surname=" + surname;
            List<User> usersSurname = Arrays.asList(restTemplate.getForObject(uriSurname, User[].class));
            for(User userName : usersName){
                for(User userSurname : usersSurname){
                    if(userName.getName().equals(userSurname.getName()) && userName.getSurname().equals(userSurname.getSurname())){
                        users.add(userName);
                    }
                }
            }
        }
        List<String> listOfString = new ArrayList<>();
        for(User user : users){
            listOfString.add(user.toString());
        }
        return listOfString;

    }

    /**
     * Crea il flusso di dati per il biglietto
     * @param idUser: id dell'utente
     * @param nameSpettacolo: titolo dello spettacolo
     * @param numeroSala: numero di sala 
     * @param posto: posto a sedere
     * @return flusso di dati per la creazione del pdf
     * @throws URISyntaxException
     * @throws IOException
     * @throws ExceptionNoUser: se non esiste l'utente
     * @throws ExceptionNoSpettacolo: se non esiste lo spettacolo
     * @throws ExceptionNoPosto: se no esiste il posto
     * @throws ExceptionNoFreePosto: se il posto è già occupato
     * @throws ExceptionNoEnoughMoney: se non ci sono abbastanza soldi
     * @throws ExceptionNoSala: se non esiste la sala
     */
    public ByteArrayOutputStream getTicketAndSee(int idUser, String nameSpettacolo, int numeroSala, Posto posto)throws URISyntaxException, IOException, ExceptionNoUser,
            ExceptionNoSpettacolo, ExceptionNoPosto, ExceptionNoEnoughMoney, ExceptionNoSala, ExceptionNoFreePosto{
        
        //recuperare l'user dall'id
        String uri1 = "http://localhost:8081/userId?id="+idUser;
        User user = restTemplate.getForObject(uri1, User.class);
        if(user == null){
            throw new ExceptionNoUser();
        }

        //recupero lo spettacolo
        String uri2 = "http://localhost:8085/Teatro/"+nameSpettacolo+"/"+numeroSala;
        Spettacolo spettacolo = restTemplate.getForObject(uri2, Spettacolo.class);
        if(spettacolo == null){
            String tmpUri2 = "http://localhost:8085/Teatro/"+nameSpettacolo;
            Spettacolo tmpSpettacolo = restTemplate.getForObject(tmpUri2, Spettacolo.class);
            if(tmpSpettacolo == null){
                throw new ExceptionNoSpettacolo();
            }
            throw new ExceptionNoSala();
        }

        //controllo se ha sufficienti soldi
        if(user.getCash() < spettacolo.getCosto()){
            throw new ExceptionNoEnoughMoney();
        }
        
        //controllo se il posto che vuole è con free == true
        List<Sala> sale = spettacolo.getSala();
        List<Posto> posti;
        boolean flag = true;
        for(int i = 0; i < sale.size(); i++){
            Sala sala = sale.get(i);
            posti = sala.getPosto();
            for(int j = 0; j < posti.size(); j++){
                Posto posto1 = posti.get(j);
                if((posto1.getColonna() == posto.getColonna()) & (posto1.getRiga().equals(posto.getRiga()))){
                    if(posto1.getFree() == false){
                        throw new ExceptionNoFreePosto();
                    }
                    flag = false;
                    break;
                }
            }
        }
        if(flag){
            throw new ExceptionNoPosto();
        }

        //Il posto è libero, lo assegno occupato
        String uri3 = "http://localhost:8085/Teatro/"+nameSpettacolo+"/"+numeroSala+"/upsetPosto?colonna="+posto.getColonna()+"&free="+posto.getFree()+"&riga="+posto.getRiga();
        RequestEntity<String> request1 = RequestEntity.post(new URI(uri3)).accept(MediaType.APPLICATION_JSON).body(null);
        ResponseEntity<String> response1 = restTemplate.exchange(request1, String.class);

        //sottraggo i soldi del costo del biglietto
        String uri4 = "http://localhost:8081/"+idUser+"/removeMoney?money=" + spettacolo.getCosto();
        RequestEntity<String> request2 = RequestEntity.post(new URI(uri4)).accept(MediaType.APPLICATION_JSON).body(null);
        ResponseEntity<String> response2 = restTemplate.exchange(request2, String.class);
        String string = "";
        string = string+ response1.getBody()+response2.getBody();

        //creo il biglietto
        String uri5 = "http://localhost:8082/Teatro/"+nameSpettacolo+"/biglietto?Posto="+posto.toStringOnlyPosto()+"&cost="+spettacolo.getCosto()+"&name="+user.getName()+"&surname="+user.getSurname()+ "&numeroSala=" + numeroSala;   
        ResponseEntity<Resource> response3 = restTemplate.exchange(RequestEntity.get(new URI(uri5)).accept(MediaType.APPLICATION_JSON).build(), Resource.class);
        InputStream inputStream = response3.getBody().getInputStream();

        //converto L'inputStream  in ByteArrayOutputStream
        int bytesRead = 0;
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        byte[] buffer = new byte[8000];
        while((bytesRead = inputStream.read(buffer)) != -1) {
            outputByteArray.write(buffer, 0, bytesRead);
        }

        return outputByteArray; 
    }
}