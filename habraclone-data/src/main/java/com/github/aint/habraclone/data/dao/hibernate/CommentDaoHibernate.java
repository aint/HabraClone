package com.github.aint.habraclone.data.dao.hibernate;

import org.springframework.stereotype.Repository;
import com.github.aint.habraclone.data.dao.inter.CommentDao;
import com.github.aint.habraclone.data.model.Comment;
import com.github.aint.habraclone.data.model.User;

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
