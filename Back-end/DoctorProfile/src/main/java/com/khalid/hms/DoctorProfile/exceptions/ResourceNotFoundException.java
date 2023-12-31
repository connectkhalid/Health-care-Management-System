package com.khalid.hms.DoctorProfile.exceptions;

public class ResourceNotFoundException extends RuntimeException{
        public ResourceNotFoundException(String message){
            super(message);
        }
}

