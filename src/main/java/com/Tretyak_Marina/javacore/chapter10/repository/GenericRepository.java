package com.Tretyak_Marina.javacore.chapter10.repository;

import java.util.List;

public interface GenericRepository<T,ID> {
    T add(T t);
    T update (T t);
    List<T> getAll();
    T getById(ID id);
    void deleteById(ID id);
    void deleteAll();
}
