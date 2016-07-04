package ua.sg.academy.java2.habraclone.dbModel.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.sql.Delete;
import ua.sg.academy.java2.habraclone.dbModel.dao.GeneralDao;
import ua.sg.academy.java2.habraclone.dbModel.entity.IEntity;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class GeneralHibernateDao implements GeneralDao {

    private Session session;
    protected Class persistentClass;

    @Override
    public IEntity getById(long id) {
        return (IEntity) getSession().load(persistentClass, id);
    }

    @Override
    public List<IEntity> getAll() {
        return getSession()
                .createCriteria(persistentClass)
                .list();
    }

    @Override
    public void save(IEntity entity) {
        getSession().save(entity);
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
                .createQuery("DELETE " + persistentClass.getName() + " e WHERE e.id = ?")
                .setLong(0, id)
                .executeUpdate();
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
        if (session == null) {
            throw new IllegalStateException("Session has not been set on DAO before usage");
        }
        return session;
    }

}
