package me.ethantu.posapp.persistence.hibernate;

import me.ethantu.posapp.domain.model.permission.IPermissionRepository;
import me.ethantu.posapp.domain.model.permission.Permission;
import me.ethantu.posapp.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 13-10-28
 * Time: PM7:44
 */
@Repository("permissionRepository")
public class PermissionRepository extends AbstractHibernateGenericRepository<Permission, Long> implements
        IPermissionRepository<Permission, Long> {

}
