package com.khalid.hms.PharmacyInventory.exceptions;

public class AuthenticationExceptionFound extends RuntimeException{
    public AuthenticationExceptionFound(String message){
        super(message);
    }
}
