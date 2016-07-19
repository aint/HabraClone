package ua.sg.academy.java2.habraclone.dao.hibernate;

import org.springframework.stereotype.Repository;
import ua.sg.academy.java2.habraclone.dao.CommentDao;
import ua.sg.academy.java2.habraclone.model.Comment;
import ua.sg.academy.java2.habraclone.model.User;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class CommentDaoHibernate extends GeneralHibernateDao implements CommentDao {

    public CommentDaoHibernate() {
        persistentClass = Comment.class;
    }

    @Override
    public List<Comment> getAllCommentsOfUser(User user) {
        return getSession()
                .getNamedQuery("getAllCommentsOfUser")
                .setEntity("author", user)
                .list();
    }

    @Override
    public Comment getLatestCommentOfUser(User user) {
        return (Comment) getSession()
                .getNamedQuery("getLatestCommentOfUser")
                .setEntity("author", user)
                .uniqueResult();
    }
}
