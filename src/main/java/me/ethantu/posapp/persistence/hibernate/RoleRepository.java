package me.ethantu.posapp.persistence.hibernate;

import me.ethantu.posapp.domain.model.role.IRoleRepository;
import me.ethantu.posapp.domain.model.role.Role;
import me.ethantu.posapp.domain.model.user.User;
import me.ethantu.posapp.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/28/14
 * Time: 3:55 PM
 */
@Repository("roleRepository")
public class RoleRepository extends AbstractHibernateGenericRepository<Role, Long> implements
        IRoleRepository<Role, Long> {

    public Set<User> findAccountAdmin() {
        Criteria criteria = getSession().createCriteria(Role.class);
        criteria.add(Restrictions.eq("role", "account-administrator"));
        criteria.setFetchMode("userSet", FetchMode.JOIN);
        Role role = (Role) criteria.uniqueResult();
        if (role != null) {
            Set<User> users = role.getUserSet();
            return users;
        }
/*                Role role = (Role)criteria.uniqueResult();
                String sql = "select user_id from user_role where role_id="+role.getId();
                List<BigInteger> userId = (List<BigInteger>)getSession().createSQLQuery(sql).list();
                List<User> userList = new ArrayList<User>();
                for (BigInteger id:userId) {
                        Criteria user = getSession().createCriteria(User.class);
                        user.add(Restrictions.eq("id",id.longValue()));
                        userList.add((User)user.uniqueResult());
                }
                if (null != userList) {
                        return userList;
                }*/
        return null;
    }
}
