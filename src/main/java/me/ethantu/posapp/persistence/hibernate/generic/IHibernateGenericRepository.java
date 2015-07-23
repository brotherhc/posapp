package me.ethantu.posapp.persistence.hibernate.generic;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Author: EthanTu
 * Date: 12-4-20
 * Time: 下午3:42
 */
public interface IHibernateGenericRepository<T, ID extends Serializable> {

    T loadById(ID id, boolean lock);

    T getById(ID id, boolean lock);

    List<T> findByExample(T exampleInstance, String... excludeProperty);

    T findByProperties(Map<String, Object> propertyMap);

    List<T> findAllByProperties(Map<String, Object> propertyMap);

    void save(T entity);

    void saveOrUpdate(T entity);

    void update(T entity);

    void delete(T entity);

    void flush();

    void clear();

    void evict(Object obj);

    void refresh(Object obj);

    Pagination<T> pagination(int page, int pageSize, Criterion[] criteria, Order[] orders,
                             Map<String, FetchMode> fetchModeMap);

    Pagination<T> pagination(int page, int pageSize, Criterion[] criteria, Map<String, String> aliasMap, Order[] orders,
                             Map<String, FetchMode> fetchModeMap);

    List<T> findAll();
}

