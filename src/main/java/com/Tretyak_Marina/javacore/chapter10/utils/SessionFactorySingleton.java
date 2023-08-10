package com.Tretyak_Marina.javacore.chapter10.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactorySingleton {
    SessionFactory sessionFactory;

    public SessionFactorySingleton() {}

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (this) {
                if (sessionFactory == null) {
                    sessionFactory = new Configuration().configure().buildSessionFactory();
                }
            }
        }
        return sessionFactory;
    }
}
