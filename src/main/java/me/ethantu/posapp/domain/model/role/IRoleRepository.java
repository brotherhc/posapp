package me.ethantu.posapp.domain.model.role;


import me.ethantu.posapp.domain.model.user.User;
import me.ethantu.posapp.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;
import java.util.Set;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/28/14
 * Time: 3:54 PM
 */
public interface IRoleRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
    Set<User> findAccountAdmin();
}
