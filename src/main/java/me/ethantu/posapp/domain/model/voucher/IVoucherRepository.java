package me.ethantu.posapp.domain.model.voucher;

import me.ethantu.posapp.domain.model.user.User;
import me.ethantu.posapp.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 2015/7/15
 * Time: 4:34
 */
public interface IVoucherRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {

    List<T> export(Date createdDate, Integer status);

}
