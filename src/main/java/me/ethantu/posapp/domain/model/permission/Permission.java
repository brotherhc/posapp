package me.ethantu.posapp.domain.model.permission;

import me.ethantu.posapp.domain.model.role.Role;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/28/14
 * Time: 3:31 PM
 */
@Entity
@Table(name = "permission")
@DynamicInsert
@DynamicUpdate
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "permission", nullable = true)
    private String permission; //权限标识 程序中判断使用,如"user:create"

    @Column(name = "description", nullable = true)
    private String description; //权限描述,UI界面显示使用

    @Column(name = "available", nullable = true)
    private Boolean available; //是否可用,如果不可用将不会添加给用户

    @ManyToMany(
            targetEntity = Role.class,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}
//            mappedBy = "permissionSet"
    )
    @JoinTable(name = "role_permission",
            joinColumns = {@JoinColumn(name = "permission_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roleSet;

    @Version
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
}
