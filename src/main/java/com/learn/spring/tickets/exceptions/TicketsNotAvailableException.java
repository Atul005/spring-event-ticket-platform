package com.learn.spring.tickets.exceptions;

public class TicketsNotAvailableException extends EventTicketException {

    public TicketsNotAvailableException() {
    }

    public TicketsNotAvailableException(String message) {
        super(message);
    }

    public TicketsNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketsNotAvailableException(Throwable cause) {
        super(cause);
    }

    public TicketsNotAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
