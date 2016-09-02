package com.github.aint.habraclone.data.dao.hibernate;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.github.aint.habraclone.data.dao.inter.HubDao;
import com.github.aint.habraclone.data.model.Hub;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class HubHibernateDao extends GeneralHibernateDao implements HubDao {

    public HubHibernateDao() {
        persistentClass = Hub.class;
    }

    @Override
    public Hub getByHubName(String name) {
        return (Hub) getSession()
                .createCriteria(persistentClass)
                .add(Restrictions.eq("name", name))
                .uniqueResult();
    }

    @Override
    public List<Hub> getMostPopularHubs() {
        return getSession()
                .createCriteria(persistentClass)
                .addOrder(Order.asc("rating"))
                .setMaxResults(10)
                .list();
    }

}
