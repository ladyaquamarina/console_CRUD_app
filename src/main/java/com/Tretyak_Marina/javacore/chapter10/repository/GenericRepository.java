package com.Tretyak_Marina.javacore.chapter10.repository;

import java.util.List;

public interface GenericRepository<T,ID> {
    public boolean addToJson(T object);
    public boolean rewriteJson(List<T> objectsList);
    public List<T> readJson();
    public T getById(ID id);
}
