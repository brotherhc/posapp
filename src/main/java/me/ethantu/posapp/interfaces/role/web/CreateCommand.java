package me.ethantu.posapp.interfaces.role.web;


import me.ethantu.posapp.domain.model.permission.Permission;
import me.ethantu.posapp.domain.model.user.User;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/31/14
 * Time: 3:41 PM
 */
public class CreateCommand implements Serializable {

    private String role;

    private String description;

    private Boolean available;

    private Set<User> userSet;

    private Set<Permission> permissionSet;

    private List<Permission> allPermissionList;

    private Long[] permissionIds;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public Set<Permission> getPermissionSet() {
        return permissionSet;
    }

    public void setPermissionSet(Set<Permission> permissionSet) {
        this.permissionSet = permissionSet;
    }

    public List<Permission> getAllPermissionList() {
        return allPermissionList;
    }

    public void setAllPermissionList(List<Permission> allPermissionList) {
        this.allPermissionList = allPermissionList;
    }

    public Long[] getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(Long[] permissionIds) {
        this.permissionIds = permissionIds;
    }
}
