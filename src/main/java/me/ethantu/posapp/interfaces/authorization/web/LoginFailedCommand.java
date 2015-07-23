package me.ethantu.posapp.interfaces.authorization.web;

import java.io.Serializable;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/29/14
 * Time: 3:22 AM
 */
public class LoginFailedCommand  implements Serializable {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
