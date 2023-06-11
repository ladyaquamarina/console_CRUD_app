package com.Tretyak_Marina.javacore.chapter10.repository;

import com.Tretyak_Marina.javacore.chapter10.model.Post;

public interface PostRepository extends GenericRepository<Post,Integer> {
    String FILENAME = "posts.json";
}

