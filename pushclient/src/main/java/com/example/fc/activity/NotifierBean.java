package com.example.fc.activity;




/**
 * 推送过来的消息bean
 * Created by zxh on 2015/3/25.
 */
public class NotifierBean  {
	/*{"title": "题目房超", 
      "content": "aaaaaaaaaaaaaaaaaaa", 
      "type": 1}*/

    /**
     * 标题
     */
    private String title;
    /**
     * 显示的内容
     */
    private String content;
    /**
     * 类型0商品列表1特卖列表2商品详情
     */
    private int type;
  

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
