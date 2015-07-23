package me.ethantu.posapp.domain.model.user;


import me.ethantu.posapp.persistence.hibernate.generic.IHibernateGenericRepository;
import org.hibernate.criterion.Criterion;

import java.io.Serializable;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 13-10-28
 * Time: PM7:43
 */
public interface IUserRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {

    User findByUsername(String username);

    Boolean exist(String username, String email);

    Boolean resetPwd(User user);

    User checkEmail(Criterion[] restrictions);

    Boolean setParent(User user);


}
