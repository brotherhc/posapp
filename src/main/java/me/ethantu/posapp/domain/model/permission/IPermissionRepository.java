package me.ethantu.posapp.domain.model.permission;



import me.ethantu.posapp.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/28/14
 * Time: 3:54 PM
 */
public interface IPermissionRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
