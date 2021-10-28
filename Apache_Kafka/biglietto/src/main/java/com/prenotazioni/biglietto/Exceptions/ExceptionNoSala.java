package com.prenotazioni.biglietto.Exceptions;

public class ExceptionNoSala extends Exception{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ExceptionNoSala() {
    }

    public ExceptionNoSala(String errorMessage){
        super(errorMessage);
    }
}