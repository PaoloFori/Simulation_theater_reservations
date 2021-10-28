package com.prenotazioni.biglietto.Controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.DocumentException;
import com.prenotazioni.biglietto.Services.GenerateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BigliettoController {
    @Autowired
    GenerateService generateService;

    /**
     * Return a PDF
     * @param spettacolo: show's title
     * @param cost: show's cost
     * @param name: user's name
     * @param surname: user's surname
     * @param posto: posto
     * @param numeroSala: number of sala
     * @return a pdf or null
     */
    @GetMapping(value = "/Teatro/{Spettacolo}/biglietto")
    public ResponseEntity<InputStreamResource> generateAndSee_PDF(@PathVariable("Spettacolo") String spettacolo, @RequestParam(value = "cost") float cost, @RequestParam(value = "name")String name, 
        @RequestParam(value = "surname")String surname,
        @RequestParam(value = "Posto")String posto,
        @RequestParam(value = "numeroSala")int numeroSala){
        try{
            ByteArrayOutputStream bis1 = generateService.createAndSee_PDF(name, surname, cost, spettacolo, posto, numeroSala);
            ByteArrayInputStream bis = new ByteArrayInputStream(bis1.toByteArray());
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
        }catch(FileNotFoundException e){
            return ResponseEntity.badRequest().body(null); 
        }catch(DocumentException e1){
            return ResponseEntity.badRequest().body(null); 
        }catch(MalformedURLException e2){
            return ResponseEntity.badRequest().body(null); 
        }catch(IOException e4){
            return ResponseEntity.badRequest().body(null); 
        }
    }
}