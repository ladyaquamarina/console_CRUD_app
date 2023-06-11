package com.Tretyak_Marina.javacore.chapter10.model;

public class Label {
    private int id;
    private String name;
    private PostStatus Status;

    public Label (int id, String name) {
        this.id = id;
        this.name = name;
        this.Status = PostStatus.ACTIVE;
    }
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public PostStatus getStatus() {
        return this.Status;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStatus(PostStatus status) {
        Status = status;
    }
}