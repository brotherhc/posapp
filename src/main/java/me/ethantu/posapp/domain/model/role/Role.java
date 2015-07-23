package me.ethantu.posapp.domain.model.role;


import me.ethantu.posapp.domain.model.permission.Permission;
import me.ethantu.posapp.domain.model.user.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/28/14
 * Time: 3:34 PM
 */
@Entity
@Table(name = "role")
@DynamicInsert
@DynamicUpdate
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role", nullable = true)
    private String role; //角色标识 程序中判断使用,如"admin"

    @Column(name = "description")
    private String description; //角色描述,UI界面显示使用

    @Column(name = "available")
    private Boolean available; //是否可用,如果不可用将不会添加给用户

    @ManyToMany(
            targetEntity = User.class,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}
//        mappedBy = "roleSet"
    )
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> userSet;

    @ManyToMany(
            targetEntity = Permission.class,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(name = "role_permission",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private Set<Permission> permissionSet;

    @Version
    private int version;

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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
