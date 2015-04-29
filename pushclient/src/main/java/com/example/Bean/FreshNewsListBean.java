package com.example.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fc on 2015/4/28.
 */
public class FreshNewsListBean extends BaseBean {
    ArrayList<FreshNews> posts;
    int count;
    int count_total;
    int pages;
    String status;

    public ArrayList<FreshNews> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<FreshNews> posts) {
        this.posts = posts;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount_total() {
        return count_total;
    }

    public void setCount_total(int count_total) {
        this.count_total = count_total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addBeans(ArrayList<FreshNews> g) {
        if (posts != null && g != null && !g.isEmpty()) {
            posts.addAll(g);
        }

    }
}
