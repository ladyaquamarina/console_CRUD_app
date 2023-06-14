package com.Tretyak_Marina.javacore.chapter10.model;

import java.util.*;

public class Writer {
    private Integer id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private PostStatus Status;

    public Writer (String firstName, String lastName){
        this.id = null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = new ArrayList<>();
        this.Status = PostStatus.ACTIVE;
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
    public PostStatus getStatus() {
        return Status;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setStatus(PostStatus status) {
        Status = status;
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