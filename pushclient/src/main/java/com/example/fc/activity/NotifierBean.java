package com.example.fc.activity;




/**
 * 推送过来的消息bean
 * Created by zxh on 2015/3/25.
 */
public class NotifierBean  {
 //   {"title": "这是标题","content":"这是,内容","type":3,"newId":3}

    /**
     * 标题
     */
    private String title;
    /**
     * 显示的内容
     */
    private String content;
    /**
     * 类型0商品列表1显示content  3.新闻
     */
    private int type;
    private  String newId;

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

  
}
