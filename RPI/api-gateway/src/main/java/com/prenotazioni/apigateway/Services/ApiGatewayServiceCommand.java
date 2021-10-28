package com.prenotazioni.apigateway.Services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.prenotazioni.apigateway.Entity.Posto;
import com.prenotazioni.apigateway.Entity.Sala;
import com.prenotazioni.apigateway.Entity.Spettacolo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiGatewayServiceCommand {

    @Autowired
    RestTemplate restTemplate;

    /**
     * Aggiunge una sala dati i vari di dati di quest'ultima e il titolo dello spettacolo
     * @param show: titolo dello spettacolo
     * @param sala: nuova sala da dover aggiungere
     * @return una stringa che indica se tutto andato bene o no
     * @throws URISyntaxException
     */
    public String addNewSalaByShow(String show, Sala sala) throws URISyntaxException{
        String uri = "http://localhost:8085/Teatro/"+show+"/addNewSala";
        RequestEntity<Sala> request = RequestEntity.post(new URI(uri)).accept(MediaType.APPLICATION_JSON).body(sala);
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        return response.getBody();
    }

    /**
     * Aggiunge sale esistenti ad un determinato show
     * @param show: titolo dello spettacolo
     * @param numeroSale: lista dei numeri delle sale da aggiungere
     * @return una stringa che indica se tutto andato bene o no
     * @throws URISyntaxException
     */
    public String addExistsSalaByShow(String show, List<Integer> numeroSale) throws URISyntaxException{
        String uri = "http://localhost:8085/Teatro/"+show+"/addExistSala?listaNumeroSale=" + numeroSale.get(0);
        for(int i = 1; i < numeroSale.size(); i++){
            uri = uri + "&listaNumeroSale=" + numeroSale.get(i);
        }
        RequestEntity<Sala> request = RequestEntity.post(new URI(uri)).accept(MediaType.APPLICATION_JSON).body(null);
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        return response.getBody();
    }

    /**
     * Elimina un film dato il nome dello show
     * @param show: titolo dello show
     * @return una stringa che indica se tutto andato bene o no
     * @throws URISyntaxException
     */
    public String deleteByShow(String show) throws URISyntaxException{
        String uri = "http://localhost:8085/Teatro/deleteShow?show="+show;
        RequestEntity<String> request = RequestEntity.method(HttpMethod.DELETE, new URI(uri)).accept(MediaType.APPLICATION_JSON).body(null);
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        return response.getBody();
    }

    /**
     * Elimina la sala di uno show
     * @param show: titolo dello show
     * @param numeroSala: numero della sala da eliminare
     * @return una stringa che indica se tutto andato bene o no
     * @throws URISyntaxException
     */
    public String deleteSalaByShowAndNumeroSala(String show, int numeroSala)throws URISyntaxException{
        String uri = "http://localhost:8085/Teatro/"+show+"/deleteSala?numeroSala="+numeroSala;
        RequestEntity<String> request = RequestEntity.method(HttpMethod.DELETE, new URI(uri)).accept(MediaType.APPLICATION_JSON).body(null);
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        return response.getBody();
    }

    /**
     * Inserisce un nuovo show con anche la prima sala
     * @param spettacolo: spettacolo da aggiungere
     * @return una stringa che indica se tutto andato bene o no
     * @throws URISyntaxException
     */
    public String inserNewShowAndNewSala(Spettacolo spettacolo) throws URISyntaxException{
        String uri = "http://localhost:8085/Teatro/addNewShowAndNewSala";
        RequestEntity<Spettacolo> request = RequestEntity.method(HttpMethod.PUT, new URI(uri)).accept(MediaType.APPLICATION_JSON).body(spettacolo);
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        return response.getBody();
    }

    /**
     * Inserisci una nuovo Show sapendo le sale
     * @param show: titolo dello show
     * @param cost: costo dello show
     * @param listNumeroSale: lista di tutte le sale da dovergli aggiungere
     * @return una stringa che indica se tutto andato bene o no
     * @throws URISyntaxException
     */
    public String inserNewShowAndKnowSala(String show, int cost, List<Integer> listNumeroSale) throws URISyntaxException{
        String uri = "http://localhost:8085/Teatro/addNewShowKnowSala?show="+show;
        for(int i : listNumeroSale){
            uri = uri + "&listNumeroSale=" + i;
        }
        uri = uri + "&cost=" +cost;
        RequestEntity<Spettacolo> request = RequestEntity.method(HttpMethod.PUT, new URI(uri)).accept(MediaType.APPLICATION_JSON).body(null);
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        return response.getBody();
    }

    /**
     * Upset posto
     * @param nameSpettacolo: titolo dello show
     * @param numeroSala: numero della sala
     * @param posto: posto da dover aggiornare
     * @return una stringa che indica se tutto andato bene o no
     * @throws URISyntaxException
     */
    public String setPosto(String nameSpettacolo, int numeroSala, Posto posto) throws URISyntaxException{
        String uri3 = "http://localhost:8085/Teatro/"+nameSpettacolo+"/"+numeroSala+"/upsetPosto?colonna="+posto.getColonna()+"&free="+posto.getFree()+"&riga="+posto.getRiga();
        RequestEntity<String> request1 = RequestEntity.post(new URI(uri3)).accept(MediaType.APPLICATION_JSON).body(null);
        ResponseEntity<String> response1 = restTemplate.exchange(request1, String.class);
        return response1.getBody();
    }

    /**
     * Permette l'inserimentodi un nuovo utente
     * @param name: nome dell'utente
     * @param surname: congome dell'utente
     * @param saldo: saldo dell'utente
     * @return una stringa che indica se tutto andato bene o no
     * @throws URISyntaxException
     */
    public String putNewUser(String name, String surname, int saldo)throws URISyntaxException{
        String uri = "http://localhost:8081/putNewUser?money="+saldo+"&name="+name+"&surname="+surname;
        RequestEntity<String> richiesta = RequestEntity.put(new URI(uri)).accept(MediaType.APPLICATION_JSON).body(null);
        ResponseEntity<String> risposta = restTemplate.exchange(richiesta, String.class);
        return risposta.getBody();
    }

    /**
     * Permette di eliminare un utente
     * @param id: id dell'utente
     * @return una stringa che indica se tutto andato bene o no
     * @throws URISyntaxException
     */
    public String deleteUser(int id)throws URISyntaxException{
        String uri = "http://localhost:8081/deleteUser?id=" + id;
        RequestEntity<String> request = RequestEntity.method(HttpMethod.DELETE, new URI(uri)).accept(MediaType.APPLICATION_JSON).body(null);
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        return response.getBody();
    }

    /**
     * Permette di aggiornare un utente
     * @param id: id dell'utente
     * @param name: nome del'utente
     * @param surname: cognome dell'utente
     * @param cash: soldi
     * @return una stringa che indica se tutto andato bene o no
     * @throws URISyntaxException
     */
    public String upsetUser(int id, String name, String surname, int cash) throws URISyntaxException{
        String uri = "http://localhost:8081/upsetUser?id=" + id + "&name=" + name + "&surname=" + surname + "&money=" + cash;
        RequestEntity<String> richiesta = RequestEntity.post(new URI(uri)).accept(MediaType.APPLICATION_JSON).body(null);
        ResponseEntity<String> risposta = restTemplate.exchange(richiesta, String.class);
        return risposta.getBody();
    }

    /**
     * Permette di aggiungere soldi ad una persona specifica
     * @param id: id dell'utente
     * @param soldi: soldi da dover aggiungere
     * @return una stringa che indica se tutto andato bene o no
     * @throws URISyntaxException
     */
    public String addMoneyUser(int id, int soldi) throws URISyntaxException{
        String uri = "http://localhost:8081/"+id+"/addMoney?money="+soldi;
        RequestEntity<String> richiesta = RequestEntity.post(new URI(uri)).accept(MediaType.APPLICATION_JSON).body(null);
        ResponseEntity<String> risposta = restTemplate.exchange(richiesta, String.class);
        return risposta.getBody();
    }

    /**
     * Rimuovi soldi da una persona
     * @param id: id dell'utente
     * @param money: soldi da dover rimuovere
     * @return una stringa che indica se tutto andato bene o no
     * @throws URISyntaxException
     */
    public String removeMoneyUser(int id, int money) throws URISyntaxException{
        String uri = "http://localhost:8081/"+id+"/removeMoney?money="+money;
        RequestEntity<String> richiesta = RequestEntity.post(new URI(uri)).accept(MediaType.APPLICATION_JSON).body(null);
        ResponseEntity<String> risposta = restTemplate.exchange(richiesta, String.class);
        return risposta.getBody();
    }
}