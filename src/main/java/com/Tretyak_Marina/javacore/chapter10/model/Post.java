package com.Tretyak_Marina.javacore.chapter10.model;

import java.util.*;
public class Post {
    private Integer id;
    private String content;
    private final Date created;
    private Date updated;
    private List<Label> labels;
    private PostStatus Status;

    public Post (String content) {
        this.id = null;
        this.content = content;
        this.created = new Date();
        this.updated = this.created;
        this.labels = new ArrayList<>();
        this.Status = PostStatus.ACTIVE;
    }
    public Integer getId() {
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
        this.updated = new Date();
    }
    public void setContent(String newContent) {
        this.content = newContent;
        this.updated = new Date();
    }
    public void setStatus(PostStatus newStatus) {
        this.Status = newStatus;
    }
    public void addLabel(Label label) {
        this.labels.add(label);
        this.updated = new Date();
    }
    public void deleteLabel(int labelId) {
        for (Label l : this.labels)
            if (l.getId() == labelId) {
                this.labels.remove(l);
                break;
            }
        this.updated = new Date();
    }
}