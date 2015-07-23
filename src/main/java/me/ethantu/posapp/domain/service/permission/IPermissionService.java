package me.ethantu.posapp.domain.service.permission;


import me.ethantu.posapp.domain.model.permission.Permission;
import me.ethantu.posapp.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/28/14
 * Time: 3:57 PM
 */
public interface IPermissionService {

    void create(Permission permission);

    void edit(Permission permission);

    void delete(Permission permission);

    Pagination<Permission> pagination(int page, int pageSize);

    Permission findById(Long id);

    List<Permission> findAll();
}
