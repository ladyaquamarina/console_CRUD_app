package com.Tretyak_Marina.javacore.chapter10.model;

public class Label {
    private Integer id;
    private String name;

    public Label (String name) {
        this.id = null;
        this.name = name;
    }
    public Label (int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}