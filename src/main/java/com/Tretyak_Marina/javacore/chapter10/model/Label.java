package com.Tretyak_Marina.javacore.chapter10.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "label", schema = "console_crud")
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "post_label",
            joinColumns = { @JoinColumn(name = "label_id") },
            inverseJoinColumns = { @JoinColumn(name = "post_id") }
    )
    private List<Post> posts;

    public Label() {}

    public Label (String name) {
        this.id = null;
        this.name = name;
    }

    public Label (Long id, String name, List<Post> posts) {
        this.id = id;
        this.name = name;
        this.posts = posts;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPost(List<Post> posts) {
        this.posts = posts;
    }
}