package ua.sg.academy.java2.habraclone.dbModel.dao.hibernate;

import ua.sg.academy.java2.habraclone.dbModel.dao.CommentDao;
import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.dbModel.entity.Comment;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;

import java.util.List;

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
