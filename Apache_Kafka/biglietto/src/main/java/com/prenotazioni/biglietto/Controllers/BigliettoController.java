package com.prenotazioni.biglietto.Controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.prenotazioni.biglietto.Exceptions.ExceptionNoPosto;
import com.prenotazioni.biglietto.Exceptions.ExceptionNoSala;
import com.prenotazioni.biglietto.Exceptions.ExceptionNoSpettacolo;
import com.prenotazioni.biglietto.Exceptions.ExceptionNoUser;
import com.prenotazioni.biglietto.Services.BigliettoServiceQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BigliettoController {

    @Autowired
    BigliettoServiceQuery bigliettoService;

    @GetMapping(value = "/Teatro/{Spettacolo}/biglietto")
    public ResponseEntity<InputStreamResource> generate_PDF(@PathVariable("Spettacolo") String spettacolo, @RequestParam(value = "idUser")int idUser,
        @RequestParam(value = "riga")String riga, @RequestParam(value = "colonna")int colonna, @RequestParam(value = "numeroSala")int numeroSala){
        try{
            ByteArrayInputStream bis = bigliettoService.create_PDF(idUser, spettacolo, colonna, riga, numeroSala);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
        }catch(DocumentException e1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch(IOException e4){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch(ExceptionNoSala e5){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch(ExceptionNoUser e6){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch(ExceptionNoSpettacolo e7){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch(ExceptionNoPosto e8){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}