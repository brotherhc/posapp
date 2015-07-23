package me.ethantu.posapp.domain.service;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 14-4-8
 * Time: PM9:33
 */
public class UnAvailableException extends RuntimeException {
    public UnAvailableException() {
        super();
    }

    public UnAvailableException(String message) {
        super(message);
    }

    public UnAvailableException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public UnAvailableException(Throwable throwable) {
        super(throwable);
    }
}
