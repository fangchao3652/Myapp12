package com.example.activity;

import android.widget.TextView;

import com.example.Bean.Course;
import com.example.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by supumall on 2015/5/23.
 */
@EActivity(R.layout.course_detail)
public class CourseDetailsActivity extends BaseActionBarActivity {
    @Extra
    Course courseBean;
    @ViewById(R.id.course_info_txv_courseName)
    TextView course_info_txv_courseName;

    @ViewById(R.id.course_info_txv_classroom)
    TextView course_info_txv_classroom;


    @ViewById(R.id.course_info_txv_teacherName)
    TextView course_info_txv_teacherName;
    @ViewById(R.id.course_info_txv_section)
    TextView course_info_txv_section;
    @ViewById(R.id.course_info_txv_week)
    TextView course_info_txv_week;

    @AfterViews
    void initview() {

        title=courseBean.getCourseName();
        course_info_txv_courseName.setText(courseBean.getCourseName());
        course_info_txv_classroom.setText(courseBean.getClasssroom());
        course_info_txv_teacherName.setText(courseBean.getTeacher());
        int week = courseBean.getDayOfWeek();

        String[] weekStrayyar = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};


        course_info_txv_section.setText(weekStrayyar[week] + " " + courseBean.getStartSection() + "-" + courseBean.getEndSection());
        course_info_txv_week.setText(courseBean.getStartWeek() + "-" + courseBean.getEndWeek() + "周");
    }
}
