package me.ethantu.posapp.interfaces.permission.web;

import java.io.Serializable;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/31/14
 * Time: 3:12 PM
 */
public class CreateCommand implements Serializable {

    private String permission;

    private String description;

    private Boolean available;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
