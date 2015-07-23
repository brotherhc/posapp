package me.ethantu.posapp.persistence.hibernate;

import me.ethantu.posapp.domain.model.user.User;
import me.ethantu.posapp.domain.model.voucher.Voucher;
import me.ethantu.posapp.domain.model.voucher.IVoucherRepository;
import me.ethantu.posapp.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.LockMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 13-10-28
 * Time: PM7:44
 */
@Repository("voucherRepository")
public class VoucherRepository extends AbstractHibernateGenericRepository<Voucher, Long> implements
        IVoucherRepository<Voucher, Long> {

        @Override
        public List<Voucher> export(Date createdDate, Integer status) {
                Criteria criteria = getSession().createCriteria(getPersistentClass());
                if (null != createdDate) {
                        criteria.add(Restrictions.ge("createdDate", createdDate));
                }
                if (null != status) {
                        criteria.add(Restrictions.eq("status", status));
                }
                criteria.setLockMode(LockMode.PESSIMISTIC_WRITE);
                criteria.setFetchMode("owner", FetchMode.JOIN);
                criteria.addOrder(Order.asc("createdDate"));
                List<Voucher> list = criteria.list();
                return null == list ? null : list;
        }



}
