package com.Tretyak_Marina.javacore.chapter10.model;

import java.util.*;
public class Post {
    private int id;
    private String content;
    private final Date created;
    private Date updated;
    private List<Label> labels;
    private PostStatus Status;

    public Post (int id, String content) {
        this.id = id;
        this.content = content;
        this.created = new Date();
        this.updated = this.created;
        this.labels = new ArrayList<>();
        this.Status = PostStatus.ACTIVE;
    }
    public int getId() {
        return this.id;
    }
    public String getContent() {
        return this.content;
    }
    public Date getCreated() {
        return this.created;
    }
    public Date getUpdated() {
        return this.updated;
    }
    public List<Label> getLabels() {
        return this.labels;
    }
    public PostStatus getStatus() {
        return this.Status;
    }

    public void setId(int newId) {
        this.id = newId;
        this.setUpdated();
    }
    public void setContent(String newContent) {
        this.content = newContent;
        this.setUpdated();
    }
    private void setUpdated() {
        this.updated = new Date();
    }
    public void setStatus(PostStatus newStatus) {
        this.Status = newStatus;
    }
    public void addLabel(Label label) {
        this.labels.add(label);
        this.setUpdated();
    }
    public void deleteLabel(int labelId) {
        for (Label l : this.labels)
            if (l.getId() == labelId) {
                this.labels.remove(l);
                break;
            }
        this.setUpdated();
    }
}