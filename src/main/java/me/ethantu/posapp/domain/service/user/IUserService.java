package me.ethantu.posapp.domain.service.user;


import me.ethantu.posapp.domain.model.user.User;
import me.ethantu.posapp.persistence.hibernate.generic.Pagination;

import java.util.Date;
import java.util.Set;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 13-10-28
 * Time: PM7:38
 */
public interface IUserService {
    void create(User user);

    void edit(User user, boolean encryptPassword);

    void delete(User user);

    public User findByUsername(String username);

    public Set<String> findRoles(String username);

    public Set<String> findPermissions(String username);

    Pagination<User> pagination(int page, int pageSize, String parent);

    User findById(Long id, boolean initializeRoleSet);

    boolean exist(String username, String email);

    boolean resetPassword(String oldPwd, String newPwd);

    User checkEmail(String name);

    void updatePassword(Long id, String pwd);

    boolean setUserParent(String name, String parentuser);
}
