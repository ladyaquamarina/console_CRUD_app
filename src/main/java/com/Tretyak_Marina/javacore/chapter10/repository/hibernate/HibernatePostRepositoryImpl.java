package com.Tretyak_Marina.javacore.chapter10.repository.hibernate;

import com.Tretyak_Marina.javacore.chapter10.utils.HibernateUtils;
import com.Tretyak_Marina.javacore.chapter10.model.Post;
import com.Tretyak_Marina.javacore.chapter10.model.PostStatus;
import com.Tretyak_Marina.javacore.chapter10.repository.PostRepository;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

public class HibernatePostRepositoryImpl implements PostRepository {

    @Override
    public Post add(Post post) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(post);
            transaction.commit();
            return post;
        }
    }

    @Override
    public Post update(Post post) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(post);
            transaction.commit();
            return post;
        }
    }

    @Override
    public List<Post> getAll() {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("from post").list();
        }
    }

    @Override
    public Post getById(Long aLong) {
        try (Session session = HibernateUtils.getSession()) {
            return (Post) session.createQuery("FROM post p" +
                    "LEFT JOIN FETCH p.writer_id" +
                    "LEFT JOIN FETCH p.labels" +
                    "WHERE p.post_id = :aLong")
                    .list().get(0);
        }
    }

    @Override
    public void deleteById(Long aLong) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            Post post = new Post();
            post.setId(aLong);
            post.setStatus(PostStatus.DELETED);
            session.update(post);
            transaction.commit();
        }
    }

    @Override
    public void deleteAll() {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("update post set status = 'deleted'").list();
            transaction.commit();
        }
    }
}
