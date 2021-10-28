package com.prenotazioni.biglietto.Exceptions;

public class ExceptionNoUser extends Exception{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ExceptionNoUser(final String errorMessage) {
        super(errorMessage);
    }

    public ExceptionNoUser(){}
}