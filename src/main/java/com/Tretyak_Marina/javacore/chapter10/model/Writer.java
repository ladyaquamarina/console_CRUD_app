package com.Tretyak_Marina.javacore.chapter10.model;

import javax.persistence.*;
import java.util.*;

public class Writer {
    private Long id;
    private String firstName;
    private String lastName;
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

    @Id
    @SequenceGenerator(name = "writer_seq", sequenceName = "writer_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "writer_seq")
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "writer", cascade = CascadeType.ALL)

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