package me.ethantu.posapp.domain.service.role;


import me.ethantu.posapp.domain.model.role.Role;
import me.ethantu.posapp.domain.model.user.User;
import me.ethantu.posapp.persistence.hibernate.generic.Pagination;

import java.util.List;
import java.util.Set;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/28/14
 * Time: 3:57 PM
 */
public interface IRoleService {
    void create(Role role);

    void edit(Role role);

    void delete(Role role);

    Pagination<Role> pagination(int page, int pageSize);

    Role findById(Long id, boolean initializePermissionSet);

    List<Role> findAll();

    Role findByRole(String role);

    Set<User> findAccountAdministrator();
}
