package ua.sg.academy.java2.habraclone.dbModel.util;

public class TestHelper {

    public static final String SELECT_COUNT_FROM_ARTICLE = "SELECT COUNT(*) FROM Article";
    public static final String SELECT_COUNT_FROM_USER = "SELECT COUNT(*) FROM User";
    public static final String SELECT_COUNT_FROM_GROUPS = "SELECT COUNT(*) FROM Comment";

    public static final String SELECT_NAME_AND_LOCATION_FROM_ACADEMY = "SELECT name, location FROM Academy";
    public static final String SELECT_NAME_AND_RATING_FROM_STUDENT = "SELECT name, rating FROM Student";

    public static void clean() {
        HibernateDaoFactory.beginTransaction();
        HibernateDaoFactory.getCurrentSession().createSQLQuery("DELETE FROM UserRole").executeUpdate();
        HibernateDaoFactory.getCurrentSession().createSQLQuery("DELETE FROM User_Hub").executeUpdate();
        HibernateDaoFactory.getCurrentSession().createSQLQuery("DELETE FROM User_Article").executeUpdate();
        HibernateDaoFactory.getCurrentSession().createQuery("DELETE FROM User").executeUpdate();
        HibernateDaoFactory.getCurrentSession().createQuery("DELETE FROM Comment").executeUpdate();
        HibernateDaoFactory.getCurrentSession().createQuery("DELETE FROM Article").executeUpdate();
        HibernateDaoFactory.commitTransaction();
    }

}
