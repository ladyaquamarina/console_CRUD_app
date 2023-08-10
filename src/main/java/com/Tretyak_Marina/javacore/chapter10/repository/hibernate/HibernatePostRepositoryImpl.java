package com.Tretyak_Marina.javacore.chapter10.repository.hibernate;

import com.Tretyak_Marina.javacore.chapter10.utils.SessionFactorySingleton;
import com.Tretyak_Marina.javacore.chapter10.model.Label;
import com.Tretyak_Marina.javacore.chapter10.model.Post;
import com.Tretyak_Marina.javacore.chapter10.model.PostStatus;
import com.Tretyak_Marina.javacore.chapter10.repository.PostRepository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HibernatePostRepositoryImpl implements PostRepository {

    SessionFactory sessionFactory = new SessionFactorySingleton().getSessionFactory();

    @Override
    public Post add(Post post) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(post);
        transaction.commit();
        session.close();
        return post;
    }

    @Override
    public Post update(Post post) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(post);
        transaction.commit();
        session.close();
        return post;
    }

    @Override
    public List<Post> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List posts = session.createQuery("from post").list();
        transaction.commit();
        session.close();
        return posts;
    }

    @Override
    public Post getById(Long aLong) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Post.class)
                .add(Restrictions.eq("id", aLong));
        List posts = criteria.list();
        Post post = (Post) posts.get(0);
        transaction.commit();
        session.close();
        return post;
    }

    @Override
    public void deleteById(Long aLong) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Post post = new Post();
        post.setId(aLong);
        post.setStatus(PostStatus.DELETED);
        session.update(post);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("update post set status = deleted").list();
        transaction.commit();
        session.close();
    }
}
