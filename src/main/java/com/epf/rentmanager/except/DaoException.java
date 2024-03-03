package com.epf.rentmanager.except;

public class DaoException extends Exception{
    public DaoException(){

    }

    public DaoException(String message){
        super(message);
    }
}
