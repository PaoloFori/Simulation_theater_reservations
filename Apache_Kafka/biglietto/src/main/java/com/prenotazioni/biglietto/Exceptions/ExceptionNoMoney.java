package com.prenotazioni.biglietto.Exceptions;

public class ExceptionNoMoney extends Exception{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ExceptionNoMoney() {
    }
    public ExceptionNoMoney(String errorMessage){
        super(errorMessage);
    }
}