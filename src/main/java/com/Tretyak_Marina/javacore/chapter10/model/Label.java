package com.Tretyak_Marina.javacore.chapter10.model;

import javax.persistence.*;

@Entity
@Table(name = "label", schema = "console_crud")
public class Label {
    private Long id;
    private String name;
    private Post post;
    public Label() {}

    public Label (String name) {
        this.id = null;
        this.name = name;
    }

    public Label (Long id, String name, Post post) {
        this.id = id;
        this.name = name;
        this.post = post;
    }

    @Id
    @SequenceGenerator(name = "label_seq", sequenceName = "label_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "label_seq")
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    public Post getPostId() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}