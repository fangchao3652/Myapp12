package com.example.greenBean;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END


import com.example.Bean.BaseBean;

/**
 * Entity mapped to table BRAND_DETAIL_BEAN.
 */
public class BrandDetailBean extends BaseBean {

    private String BrandID;
    private String BrandName;
    private String CategoryList;
    private Integer SortOrder;
    private String Image;

    // KEEP FIELDS - put your custom fields here
    boolean flag_show_indictor;//是否显示左侧的红色选中标识

    public boolean isFlag_show_indictor() {
        return flag_show_indictor;
    }

    public void setFlag_show_indictor(boolean flag_show_indictor) {
        this.flag_show_indictor = flag_show_indictor;
    }
    // KEEP FIELDS END

    public BrandDetailBean() {
    }

    public BrandDetailBean(String BrandID) {
        this.BrandID = BrandID;
    }

    public BrandDetailBean(String BrandID, String BrandName, String CategoryList, Integer SortOrder, String Image) {
        this.BrandID = BrandID;
        this.BrandName = BrandName;
        this.CategoryList = CategoryList;
        this.SortOrder = SortOrder;
        this.Image = Image;
    }

    public String getBrandID() {
        return BrandID;
    }

    public void setBrandID(String BrandID) {
        this.BrandID = BrandID;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String BrandName) {
        this.BrandName = BrandName;
    }

    public String getCategoryList() {
        return CategoryList;
    }

    public void setCategoryList(String CategoryList) {
        this.CategoryList = CategoryList;
    }

    public Integer getSortOrder() {
        return SortOrder;
    }

    public void setSortOrder(Integer SortOrder) {
        this.SortOrder = SortOrder;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
