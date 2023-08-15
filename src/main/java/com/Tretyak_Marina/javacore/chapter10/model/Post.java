package com.Tretyak_Marina.javacore.chapter10.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "post", schema = "console_crud")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated")
    private Date updated;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "writer_id")
    private Writer writer;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "post_label",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "label_id") }
    )
    private List<Label> labels;

    @Column(name = "status")
    private PostStatus Status;

    public Post() {}
    public Post (String content) {
        this.id = null;
        this.content = content;
        this.created = new Date();
        this.updated = this.created;
        this.labels = new ArrayList<>();
        this.Status = PostStatus.ACTIVE;
    }
    public Post (long id, String content, Date created, Date updated, Writer writer, List<Label> labels, PostStatus status) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.writer = writer;
        this.labels = labels;
        this.Status = status;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String newContent) {
        this.content = newContent;
        setUpdated(new Date());
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public PostStatus getStatus() {
        return this.Status;
    }

    public void setStatus(PostStatus newStatus) {
        this.Status = newStatus;
        setUpdated(new Date());
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
        setUpdated(new Date());
    }

    public List<Label> getLabels() {
        return this.labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public void addLabel(Label label) {
        this.labels.add(label);
        setUpdated(new Date());
    }

    public void deleteLabel(long labelId) {
        for (Label l : this.labels)
            if (l.getId() == labelId) {
                this.labels.remove(l);
                break;
            }
        setUpdated(new Date());
    }
}