package com.huiming.emeng.model;

public class Navigation {
    private Integer id;

    private Byte position;

    private String document;

    private String link;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getPosition() {
        return position;
    }

    public void setPosition(Byte position) {
        this.position = position;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document == null ? null : document.trim();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    @Override
    public String toString() {
        return "Navigation{" +
                "id=" + id +
                ", position=" + position +
                ", document='" + document + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}