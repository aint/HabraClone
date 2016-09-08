package com.github.aint.habraclone.data.dao.hibernate;

import org.springframework.stereotype.Repository;
import com.github.aint.habraclone.data.dao.inter.CommentDao;
import com.github.aint.habraclone.data.model.Comment;
import com.github.aint.habraclone.data.model.User;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class CommentHibernateDao extends GeneralHibernateDao implements CommentDao {

    public CommentHibernateDao() {
        persistentClass = Comment.class;
    }

    @Override
    public List<Comment> getAllCommentsOfUser(User user) {
        return getSession()
                .getNamedQuery("getAllCommentsOfUser")
                .setEntity("author", user)
                .list();
    }

}
