package com.example.greenBean;


// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

import com.example.Bean.BaseBean;

/**
 * Entity mapped to table DISTRICT_BEAN.
 */
public class DistrictBean extends BaseBean {

    private String AreaCode;
    private String AreaName;
    private String ParentID;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public DistrictBean() {
    }

    public DistrictBean(String AreaCode) {
        this.AreaCode = AreaCode;
    }

    public DistrictBean(String AreaCode, String AreaName, String ParentID) {
        this.AreaCode = AreaCode;
        this.AreaName = AreaName;
        this.ParentID = ParentID;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String AreaCode) {
        this.AreaCode = AreaCode;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String AreaName) {
        this.AreaName = AreaName;
    }

    public String getParentID() {
        return ParentID;
    }

    public void setParentID(String ParentID) {
        this.ParentID = ParentID;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
