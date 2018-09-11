package com.almightee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Pattern {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String author;
    private String imageUrl;

    //information que nous ne souhaitons pas exposer
    //@JsonIgnore
    private int price;

    public Pattern() {
    }

    public Pattern(int id, String name, String author, String imageUrl, int price) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Pattern{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", author='" + author + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", price=" + price +
            '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
