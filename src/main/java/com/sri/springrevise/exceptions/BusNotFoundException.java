package com.sri.springrevise.exceptions;

public class BusNotFoundException extends RuntimeException {
    public BusNotFoundException(String s) {
        super(s);
    }
}
