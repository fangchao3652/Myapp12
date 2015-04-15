package com.example.common;

import com.example.greenBean.BrandDetailBean;

import java.util.Comparator;


/**品牌信息 按拼音顺序排序
 * Created by Fangchao on 2015/2/3.
 */
public class PinyinComparator implements Comparator<BrandDetailBean> {
    public int compare(BrandDetailBean b1, BrandDetailBean b2) {
        return StringUtils
                .getPinYinHeadChar(b1.getBrandName())
                .toLowerCase()
                .compareTo(
                        StringUtils.getPinYinHeadChar(b2.getBrandName())
                                .toLowerCase());
    }
}
