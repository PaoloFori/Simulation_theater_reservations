package com.prenotazioni.biglietto.Exceptions;

public class ExceptionNoSpettacolo extends Exception{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ExceptionNoSpettacolo(String errorMessage) {
        super(errorMessage);
    }
    public ExceptionNoSpettacolo(){}
}