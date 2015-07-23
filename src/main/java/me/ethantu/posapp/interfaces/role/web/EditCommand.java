package me.ethantu.posapp.interfaces.role.web;

import me.ethantu.posapp.domain.model.permission.Permission;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/31/14
 * Time: 3:41 PM
 */
public class EditCommand implements Serializable {

    private Long id;

    private String role;

    private String description;

    private Boolean available;

    private Set<Permission> permissionSet;

    private int version;

    private List<Permission> allPermissionList;

    private Long[] permissionIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Set<Permission> getPermissionSet() {
        return permissionSet;
    }

    public void setPermissionSet(Set<Permission> permissionSet) {
        this.permissionSet = permissionSet;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
