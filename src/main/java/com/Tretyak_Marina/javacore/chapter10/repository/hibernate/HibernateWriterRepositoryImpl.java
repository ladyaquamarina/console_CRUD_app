package com.Tretyak_Marina.javacore.chapter10.repository.hibernate;

import com.Tretyak_Marina.javacore.chapter10.utils.HibernateUtils;
import com.Tretyak_Marina.javacore.chapter10.model.Writer;
import com.Tretyak_Marina.javacore.chapter10.repository.WriterRepository;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

public class HibernateWriterRepositoryImpl implements WriterRepository {

    @Override
    public Writer add(Writer writer) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(writer);
            transaction.commit();
            return writer;
        }
    }

    @Override
    public Writer update(Writer writer) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(writer);
            transaction.commit();
            session.close();
            return writer;
        }
    }

    @Override
    public List<Writer> getAll() {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("from writer").list();
        }
    }

    @Override
    public Writer getById(Long aLong) {
        try (Session session = HibernateUtils.getSession()) {
            return (Writer) session.createQuery("FROM writer w " +
                    "LEFT JOIN FETCH w.posts " +
                    "WHERE w.writer_id = :aLong")
                    .list().get(0);
        }
    }

    @Override
    public void deleteById(Long aLong) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            Writer writer = new Writer();
            writer.setId(aLong);
            writer.setPosts(null);
            session.delete(writer);
            transaction.commit();
        }
    }

    @Override
    public void deleteAll() {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from writer").executeUpdate();
            transaction.commit();
        }
    }
}