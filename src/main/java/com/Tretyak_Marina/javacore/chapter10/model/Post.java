package com.Tretyak_Marina.javacore.chapter10.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "post", schema = "console_crud")
public class Post {
    private Long id;
    private String content;
    private Date created;
    private Date updated;
    private Writer writer;
    private List<Label> labels;
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

    @Id
    @SequenceGenerator(name = "post_seq", sequenceName = "post_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "content")
    public String getContent() {
        return this.content;
    }

    public void setContent(String newContent) {
        this.content = newContent;
        setUpdated(new Date());
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated")
    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Column(name = "status")
    public PostStatus getStatus() {
        return this.Status;
    }

    public void setStatus(PostStatus newStatus) {
        this.Status = newStatus;
        setUpdated(new Date());
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "writer_id")
    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
        setUpdated(new Date());
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
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