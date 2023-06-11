package com.Tretyak_Marina.javacore.chapter10.repository;

import com.Tretyak_Marina.javacore.chapter10.model.Label;

public interface LabelRepository extends GenericRepository<Label,Integer> {
    String FILENAME = "labels.json";
}

