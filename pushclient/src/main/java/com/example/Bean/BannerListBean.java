package com.example.Bean;

import java.util.List;

/**
 * Created by fc on 2015/4/27.
 */
public class BannerListBean extends BaseBean {
    /**
     * 分页信息bean
     */
    private PageInfoBean PageInfo;
    private List<BannerBean> NewsList;

    public PageInfoBean getPageInfo() {
        return PageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        PageInfo = pageInfo;
    }

    public List<BannerBean> getNewsList() {
        return NewsList;
    }

    public void setNewsList(List<BannerBean> newsList) {
        NewsList = newsList;
    }

    public void addNewsBeans(List<BannerBean> g) {
        if (NewsList != null && g != null && !g.isEmpty()) {
            NewsList.addAll(g);
        }
    }

}
