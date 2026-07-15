package com.db.ar.exceptions;

public class VendorOrderNotFoundException extends RuntimeException {
    public VendorOrderNotFoundException(String message) {
        super(message);
    }
}
