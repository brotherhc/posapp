package me.ethantu.posapp.interfaces.permission.web;

import java.io.Serializable;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/31/14
 * Time: 3:14 PM
 */
public class EditCommand implements Serializable {

    private Long id;

    private String permission;

    private String description;

    private Boolean available;

    private int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
