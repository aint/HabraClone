package com.github.aint.habraclone.data.dao.hibernate;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.github.aint.habraclone.data.dao.inter.UserDao;
import com.github.aint.habraclone.data.model.User;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class UserHibernateDao extends GeneralHibernateDao implements UserDao {

    public UserHibernateDao() {
        persistentClass = User.class;
    }

    @Override
    public User getByUserName(String username) {
        return (User) getSession()
                .createCriteria(persistentClass)
                .add(Restrictions.eq("username", username))
                .uniqueResult();
    }

    @Override
    public User getByEmail(String email) {
        return (User) getSession()
                .createCriteria(persistentClass)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public List<User> getNonActivatedUsers() {
        return getSession()
                .createCriteria(persistentClass)
                .add(Restrictions.eq("enabled", false))
                .list();
    }
}
