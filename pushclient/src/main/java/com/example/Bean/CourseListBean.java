package com.example.Bean;

import java.util.List;

/**
 * Created by fc on 2015/5/21.
 */
public class CourseListBean extends BaseBean {

    private List<Course> courseList;

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }
}
