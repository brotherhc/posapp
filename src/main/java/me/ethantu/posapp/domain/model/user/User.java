package me.ethantu.posapp.domain.model.user;


import me.ethantu.posapp.domain.model.role.Role;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 13-10-28
 * Time: PM7:38
 */
@Entity
@Table(name = "user")
@DynamicInsert
@DynamicUpdate
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name", length = 16, unique = true, nullable = true, updatable = false)
    private String username;


    @Column(name = "password", length = 32, nullable = true)
    private String password;

    @Column(name = "real_name", length = 8)
    private String realName;

    @Column(name = "salt", nullable = true)
    private String salt;

    @Column(name = "signed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date signedDate;

    @Column(name = "last_signed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSignedDate;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Column(name = "available")
    private Boolean available;

    @ManyToMany(
            targetEntity = Role.class,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roleSet;

    @ManyToOne(
            targetEntity = User.class,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_parent_user"))
    private User parent;


    @Version
    private int version;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCredentialsSalt() {
        return username + salt;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(Date signedDate) {
        this.signedDate = signedDate;
    }

    public Date getLastSignedDate() {
        return lastSignedDate;
    }

    public void setLastSignedDate(Date lastSignedDate) {
        this.lastSignedDate = lastSignedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
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

    public User getParent() { return parent; }

    public void setParent(User parent) { this.parent = parent; }
}
