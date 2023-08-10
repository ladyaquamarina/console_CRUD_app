package com.Tretyak_Marina.javacore.chapter10.repository.hibernate;

import com.Tretyak_Marina.javacore.chapter10.utils.SessionFactorySingleton;
import com.Tretyak_Marina.javacore.chapter10.model.Label;
import com.Tretyak_Marina.javacore.chapter10.repository.LabelRepository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class HibernateLabelRepositoryImpl implements LabelRepository {

    SessionFactory sessionFactory = new SessionFactorySingleton().getSessionFactory();

    @Override
    public Label add(Label label) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(label);
        transaction.commit();
        session.close();
        return label;
    }

    @Override
    public Label update(Label label) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(label);
        transaction.commit();
        session.close();
        return label;
    }

    @Override
    public List<Label> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List labels = session.createQuery("from label").list();
        transaction.commit();
        session.close();
        return labels;
    }

    @Override
    public Label getById(Long aLong) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Label.class)
                .add(Restrictions.eq("id", aLong));
        List labels = criteria.list();
        Label label = (Label) labels.get(0);
        transaction.commit();
        session.close();
        return label;
    }

    @Override
    public void deleteById(Long aLong) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Label label = new Label();
        label.setId(aLong);
        label.setPost(null);
        session.delete(label);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from label").executeUpdate();
        transaction.commit();
        session.close();
    }
}