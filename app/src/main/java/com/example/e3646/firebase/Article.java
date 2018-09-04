package com.example.e3646.firebase;

public class Article {

    private String article_content;
    private String article_id;
    private String article_tag;
    private String article_title;
    private String author;
    private String created_time;
    private String author_tag;

    public Article(String article_content, String article_id, String article_tag, String article_title, String author, String created_time, String search) {

        this.article_content = article_content;
        this.article_id = article_id;
        this.article_tag = article_tag;
        this.article_title = article_title;
        this.author = author;
        this.created_time = created_time;
        this.author_tag = search;

    }

    public String getArticle_content() {
        return article_content;
    }

    public void setArticle_content(String article_content) {
        this.article_content = article_content;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getArticle_tag() {
        return article_tag;
    }

    public void setArticle_tag(String article_tag) {
        this.article_tag = article_tag;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getAuthor_tag() {
        return author_tag;
    }

    public void setAuthor_tag(String author_tag) {
        this.author_tag = author_tag;
    }

}
