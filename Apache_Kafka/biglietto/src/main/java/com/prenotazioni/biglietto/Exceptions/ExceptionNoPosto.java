package com.prenotazioni.biglietto.Exceptions;

public class ExceptionNoPosto extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ExceptionNoPosto(){

    }

    public ExceptionNoPosto(String errorMessage){
        super(errorMessage);
    }
}