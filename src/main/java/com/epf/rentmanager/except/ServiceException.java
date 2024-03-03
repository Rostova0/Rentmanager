package com.epf.rentmanager.except;

public class ServiceException extends Exception{
    public ServiceException(){

    }

    public ServiceException(String message){
        super(message);
    }
}
