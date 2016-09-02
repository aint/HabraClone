package com.github.aint.habraclone.data.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.github.aint.habraclone.data.dao.inter.GeneralDao;
import com.github.aint.habraclone.data.model.IEntity;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public abstract class GeneralHibernateDao implements GeneralDao {

    private Session session;
    private SessionFactory sessionFactory;
    protected Class persistentClass;

    @Override
    public IEntity getById(long id) {
        return (IEntity) getSession().get(persistentClass, id);
    }

    @Override
    public Long save(IEntity entity) {
        return (Long) getSession().save(entity);
    }

    @Override
    public void update(IEntity entity) {
        getSession().update(entity);
    }

    @Override
    public void delete(IEntity entity) {
        getSession().delete(entity);
    }

    @Override
    public void deleteById(long id) {
        getSession()
                .createQuery("DELETE " + persistentClass.getName() + " e WHERE e.id = :id")
                .setLong("id", id)
                .executeUpdate();
    }

    @Override
    public List<? extends IEntity> getAll() {
        return getSession()
                .createCriteria(persistentClass)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    public List<? extends IEntity> getAllSortedDeskByRating() {
        return getSession()
                .createCriteria(persistentClass)
                .addOrder(Order.desc("rating"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    public void flush() {
        getSession().flush();
    }

    public void clear() {
        getSession().clear();
    }

    public void setSession(Session s) {
        this.session = s;
    }

    protected Session getSession() {
        session = sessionFactory.getCurrentSession();
//                openSession();
        if (session == null) {
            throw new IllegalStateException("Session has not been set on DAO before usage");
        }
        return session;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}