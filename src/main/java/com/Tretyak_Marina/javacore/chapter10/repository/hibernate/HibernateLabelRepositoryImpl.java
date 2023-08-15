package com.Tretyak_Marina.javacore.chapter10.repository.hibernate;

import com.Tretyak_Marina.javacore.chapter10.utils.HibernateUtils;
import com.Tretyak_Marina.javacore.chapter10.model.Label;
import com.Tretyak_Marina.javacore.chapter10.repository.LabelRepository;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateLabelRepositoryImpl implements LabelRepository {

    @Override
    public Label add(Label label) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(label);
            transaction.commit();
            return label;
        }
    }

    @Override
    public Label update(Label label) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(label);
            transaction.commit();
            return label;
        }
    }

    @Override
    public List<Label> getAll() {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("from label").list();
        }
    }

    @Override
    public Label getById(Long aLong) {
        try (Session session = HibernateUtils.getSession()) {
            return (Label) session.createQuery("FROM label l " +
                    "LEFT JOIN FETCH l.post_id " +
                    "WHERE l.label_id = :aLong").
                    list().get(0);
        }
    }

    @Override
    public void deleteById(Long aLong) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            Label label = new Label();
            label.setId(aLong);
            label.setPost(null);
            session.delete(label);
            transaction.commit();
        }
    }

    @Override
    public void deleteAll() {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from label").executeUpdate();
            transaction.commit();
        }
    }
}