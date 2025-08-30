package com.whoami.milkcheque.exception;

import java.lang.RuntimeException;

public class MenuItemRetrievalException extends RuntimeException {
    public MenuItemRetrievalException(String message) {
        super(message);
    }
}
