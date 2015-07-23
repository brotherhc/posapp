package me.ethantu.posapp.persistence.hibernate;

import me.ethantu.posapp.domain.model.user.IUserRepository;
import me.ethantu.posapp.domain.model.user.User;
import me.ethantu.posapp.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 13-10-28
 * Time: PM7:44
 */
@Repository("userRepository")
public class UserRepository extends AbstractHibernateGenericRepository<User, Long> implements
        IUserRepository<User, Long> {

    @Override
    public User findByUsername(String username) {

        Map<String, Object> propertyMap = new HashMap<String, Object>();
        propertyMap.put("username", username);

        return findByProperties(propertyMap);
    }

    public Boolean exist(String username, String email) {

        Criteria criteria = getSession().createCriteria(getPersistentClass());

        criteria.add(Restrictions.or(Restrictions.eq("username", username), Restrictions.eq("email", email)))
                .setMaxResults(1);
        Object result = criteria.uniqueResult();
        return null != result;
    }

    public Boolean resetPwd(User user) {
        try {
            getSession().update(user);
            return true;
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    @Override
    public User checkEmail(Criterion[] restrictions) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        if (restrictions != null) {
            for (Criterion criterion : restrictions) {
                criteria.add(criterion);
            }
        }

        User user = (User) criteria.uniqueResult();
        return user;
    }

    public Boolean setParent(User user) {
        try {
            getSession().update(user);
            return true;
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

}
