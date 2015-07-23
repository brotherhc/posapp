package me.ethantu.posapp.interfaces.user.web;

import me.ethantu.posapp.domain.model.role.Role;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 13-11-7
 * Time: PM3:14
 */
public class CreateCommand implements Serializable {

    @NotBlank()
    @Length(min=4, max=16)
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String realName;

    private String parentUsername;

    @NotNull
    private boolean available;

    private List<Role> allRoleList;

    private Long[] roleIds;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getParentUsername() {
        return parentUsername;
    }

    public void setParentUsername(String parentUsername) {
        this.parentUsername = parentUsername;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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
