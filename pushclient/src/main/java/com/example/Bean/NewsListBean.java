package com.example.Bean;

import java.util.List;

/**
 * Created by supumall on 2015/4/27.
 */
public class NewsListBean extends BaseBean {
    /**
     * 分页信息bean
     */
    private PageInfoBean PageInfo;
    private List<NewBaseBean> NewsList;

    public PageInfoBean getPageInfo() {
        return PageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        PageInfo = pageInfo;
    }

    public List<NewBaseBean> getNewsList() {
        return NewsList;
    }

    public void setNewsList(List<NewBaseBean> newsList) {
        NewsList = newsList;
    }

    public void addNewsBeans(List<NewBaseBean> g) {
        if (NewsList != null && g != null && !g.isEmpty()) {
            NewsList.addAll(g);
        }
    }

}
