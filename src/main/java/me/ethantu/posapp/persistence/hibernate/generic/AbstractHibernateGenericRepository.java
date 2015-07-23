package me.ethantu.posapp.persistence.hibernate.generic;


import org.hibernate.*;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;


/**
 * Author: EthanTu
 * Date: 12-4-20
 * Time: 下午3:43
 */
@Repository
public abstract class AbstractHibernateGenericRepository<T, ID extends Serializable> implements IHibernateGenericRepository<T, ID> {

    private static final String DELETE_FLAG_PROPERTY = "deleted";

    private Class<T> persistentClass;

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public AbstractHibernateGenericRepository() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    @Override
    public void clear() {
        getSession().clear();
    }

    @Override
    public void evict(Object obj) {
        getSession().evict(obj);
    }

    @Override
    public void refresh(Object obj) {
        getSession().refresh(obj);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T loadById(ID id, boolean lock) {
        T entity;
        if (lock) {
            entity = (T) getSession().load(getPersistentClass(), id, LockOptions.UPGRADE);
        } else {
            entity = (T) getSession().load(getPersistentClass(), id);
        }

        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getById(ID id, boolean lock) {
        T entity;
        if (lock) {
            entity = (T) getSession().get(getPersistentClass(), id, LockOptions.UPGRADE);
        } else {
            entity = (T) getSession().get(getPersistentClass(), id);
        }

        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance, String... excludeProperty) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        Example example = Example.create(exampleInstance);
        for (String exclude : excludeProperty) {
            example.excludeProperty(exclude);
        }
        criteria.add(example);
        return criteria.list();
    }


    @Override
    @SuppressWarnings("unchecked")
    public T findByProperties(Map<String, Object> propertyMap) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
            criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
        }

        Object result = criteria.uniqueResult();
        return null == result ? null : (T) result;
    }

    @Override
    public List<T> findAllByProperties(Map<String, Object> propertyMap) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
            criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
        }
        return criteria.list();
    }

    @Override
    public void flush() {
        getSession().flush();
    }

    @Override
    public void save(T entity) {
        getSession().save(entity);
    }

    @Override
    public void update(T entity) {
        getSession().update(entity);
    }

    @Override
    public void saveOrUpdate(T entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Pagination<T> pagination(int page, int pageSize, Criterion[] criteria, Order[] orders,
                                    Map<String, FetchMode> fetchModeMap) {
        return pagination(page, pageSize, criteria, null, orders, fetchModeMap);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Pagination<T> pagination(int page, int pageSize, Criterion[] criteria, Map<String, String> aliasMap, Order[] orders,
                                    Map<String, FetchMode> fetchModeMap) {

        Criteria criteriaCount = getSession().createCriteria(getPersistentClass()).
                setProjection(Projections.rowCount());

        Criteria criteriaSearch = getSession().createCriteria(getPersistentClass());

        if (null != aliasMap) {
            for (Map.Entry<String, String> entry : aliasMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                criteriaCount.createAlias(key, value);
                criteriaSearch.createAlias(key, value);
            }
        }

        if (criteria != null) {
            for (Criterion criterion : criteria) {
                criteriaCount.add(criterion);
                criteriaSearch.add(criterion);
            }
        }

        if (orders != null) {
            for (Order order : orders) {
                criteriaSearch.addOrder(order);
            }
        }

        if (fetchModeMap != null) {
            for (Map.Entry<String, FetchMode> entry : fetchModeMap.entrySet()) {
                String key = entry.getKey();
                FetchMode value = entry.getValue();
                criteriaCount.setFetchMode(key, value);
                criteriaSearch.setFetchMode(key, value);
            }
        }

        int count = ((Long) criteriaCount.uniqueResult()).intValue();

        int firstResult = (page - 1) * pageSize;

        criteriaSearch.setFirstResult(firstResult).setMaxResults(pageSize);

        return new Pagination<T>(criteriaSearch.list(), count, page, pageSize);
    }

    @Override
    public List<T> findAll() {
        return getSession().createCriteria(getPersistentClass()).list();
    }






}