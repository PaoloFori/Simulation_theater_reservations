package com.prenotazioni.utente.Exception;

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