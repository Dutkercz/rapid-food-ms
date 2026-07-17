package com.db.ar.exception;

public class VendorOrderNotFoundException extends RuntimeException {
    public VendorOrderNotFoundException(String message) {
        super(message);
    }
}
