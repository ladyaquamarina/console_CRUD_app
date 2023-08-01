package com.Tretyak_Marina.javacore.chapter10.model;

import java.util.*;

public class Writer {
    private Integer id;
    private String firstName;
    private String lastName;
    private List<Post> posts;

    public Writer (String firstName, String lastName){
        this.id = null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = new ArrayList<>();
    }
    public Writer (int id, String firstName, String lastName, List<Post> posts){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return this.id;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public List<Post> getPosts() {
        return this.posts;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }
    public void deletePost(int postId) {
        for (Post p : this.posts)
            if (p.getId() == postId) {
                this.posts.remove(p);
                break;
            }
    }
}