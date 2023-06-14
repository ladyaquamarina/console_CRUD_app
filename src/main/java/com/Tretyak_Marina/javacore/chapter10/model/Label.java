package com.Tretyak_Marina.javacore.chapter10.model;

public class Label {
    private Integer id;
    private String name;
    private PostStatus Status;

    public Label (String name) {
        this.id = null;
        this.name = name;
        this.Status = PostStatus.ACTIVE;
    }
    public Integer getId() {
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