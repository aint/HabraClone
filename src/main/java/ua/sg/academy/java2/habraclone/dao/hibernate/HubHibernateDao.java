package ua.sg.academy.java2.habraclone.dao.hibernate;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.sg.academy.java2.habraclone.dao.HubDao;
import ua.sg.academy.java2.habraclone.model.Hub;

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
