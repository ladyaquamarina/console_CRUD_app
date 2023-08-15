package com.Tretyak_Marina.javacore.chapter10.model;

import javax.persistence.*;
import java.util.*;

public class Writer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "writer", cascade = CascadeType.ALL)
    private List<Post> posts;

    public Writer() {}

    public Writer (String firstName, String lastName){
        this.id = null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = new ArrayList<>();
    }

    public Writer (long id, String firstName, String lastName, List<Post> posts){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Post> getPosts() {
        return this.posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void deletePost(long postId) {
        for (Post p : this.posts)
            if (p.getId() == postId) {
                this.posts.remove(p);
                break;
            }
    }
}