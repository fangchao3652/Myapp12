package com.example.Bean;

/**
 * Created by fc on 2015/4/27.
 */
public class BannerBean extends BaseBean{
    int Id;
    String author;
    String newId;
    String source;
    String newBodyHtml;
    String publishTime;
    String title;
    String imageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNewBodyHtml() {
        return newBodyHtml;
    }

    public void setNewBodyHtml(String newBodyHtml) {
        this.newBodyHtml = newBodyHtml;
    }
}
