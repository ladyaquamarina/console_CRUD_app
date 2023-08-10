package com.Tretyak_Marina.javacore.chapter10.repository.hibernate;

import com.Tretyak_Marina.javacore.chapter10.utils.SessionFactorySingleton;
import com.Tretyak_Marina.javacore.chapter10.model.Post;
import com.Tretyak_Marina.javacore.chapter10.model.Writer;
import com.Tretyak_Marina.javacore.chapter10.repository.WriterRepository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HibernateWriterRepositoryImpl implements WriterRepository {

    SessionFactory sessionFactory = new SessionFactorySingleton().getSessionFactory();

    @Override
    public Writer add(Writer writer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(writer);
        transaction.commit();
        session.close();
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(writer);
        transaction.commit();
        session.close();
        return writer;
    }

    @Override
    public List<Writer> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List writers = session.createQuery("from writer").list();
        transaction.commit();
        session.close();
        return writers;
    }

    @Override
    public Writer getById(Long aLong) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Writer.class)
                .add(Restrictions.eq("id", aLong));
        List writers = criteria.list();
        Writer writer = (Writer) writers.get(0);
        transaction.commit();
        session.close();
        return writer;
    }

    @Override
    public void deleteById(Long aLong) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Writer writer = new Writer();
        writer.setId(aLong);
        writer.setPosts(null);
        session.delete(writer);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from writer").executeUpdate();
        transaction.commit();
        session.close();
    }
}
