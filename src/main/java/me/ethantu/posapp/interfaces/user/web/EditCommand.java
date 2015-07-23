package me.ethantu.posapp.interfaces.user.web;

import me.ethantu.posapp.domain.model.role.Role;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 13-11-7
 * Time: PM3:14
 */
public class EditCommand implements Serializable {

    private Long id;
    private String username;

    private String password;
    private String newPassword;
    private String confirmNewPassword;
    @NotNull
    private boolean available;
    private Set<Role> roleSet;
    private int version;

    private List<Role> allRoleList;

    private Long[] roleIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<Role> getAllRoleList() {
        return allRoleList;
    }

    public void setAllRoleList(List<Role> allRoleList) {
        this.allRoleList = allRoleList;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }
}
