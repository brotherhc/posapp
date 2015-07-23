package me.ethantu.posapp.commons.shiro;

import org.apache.shiro.authc.AccountException;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/29/14
 * Time: 3:13 PM
 */
public class VerificationCodeException extends AccountException {

    public VerificationCodeException() {
        super();
    }

    public VerificationCodeException(String message) {
        super(message);
    }

    public VerificationCodeException(Throwable cause) {
        super(cause);
    }

    public VerificationCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
