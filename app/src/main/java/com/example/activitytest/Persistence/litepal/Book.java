package com.example.activitytest.Persistence.litepal;


import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Book extends LitePalSupport implements Serializable {
    private int id;
    private String author;
    private String name;
    private Double price;
    private int page;
    private String press;

    public void setPress(String press) {
        this.press = press;
    }

    public String getPress() {
        return press;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Book(int id, String author, String name, Double price, int page,String press) {
        this.id = id;
        this.author = author;
        this.name = name;
        this.price = price;
        this.page = page;
        this.press=press;
    }

    public Book() { }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", page=" + page +
                '}';
    }
}
