package com.whoami.milkcheque.exception;

import java.lang.RuntimeException;

public class StoreInfoRetrievalException extends RuntimeException {
    public StoreInfoRetrievalException(String message) {
        super(message);
    }
}
