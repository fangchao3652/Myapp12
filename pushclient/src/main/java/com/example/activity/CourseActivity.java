package com.example.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.Bean.Course;
import com.example.Bean.CourseListBean;
import com.example.Bean.ResultSingleBean;
import com.example.R;
import com.example.common.CustomToast;
import com.example.common.SharedPreferencesUtils;
import com.example.data.DataHelper;
import com.example.data.PostDataHelper;
import com.example.data.URLHelper;
import com.example.data.VolleyResponseHelper;

import org.json.JSONObject;

/**
 * Created by supumall on 2015/5/21.
 */
public class CourseActivity extends BaseActionBarActivity implements DataHelper.DataListener {
    CourseListBean listdata;
    // private CourseService courseService;
    LinearLayout ll_view_setweek;


    MaterialDialog dialog;
    TextView tv_setweek;
    //课程页面的button引用，6行7列
    private int[][] lessons = {
            {R.id.lesson11, R.id.lesson12, R.id.lesson13, R.id.lesson14, R.id.lesson15, R.id.lesson16, R.id.lesson17},
            {R.id.lesson21, R.id.lesson22, R.id.lesson23, R.id.lesson24, R.id.lesson25, R.id.lesson26, R.id.lesson27},
            {R.id.lesson31, R.id.lesson32, R.id.lesson33, R.id.lesson34, R.id.lesson35, R.id.lesson36, R.id.lesson37},
            {R.id.lesson41, R.id.lesson42, R.id.lesson43, R.id.lesson44, R.id.lesson45, R.id.lesson46, R.id.lesson47},
            {R.id.lesson51, R.id.lesson52, R.id.lesson53, R.id.lesson54, R.id.lesson55, R.id.lesson56, R.id.lesson57},
            {R.id.lesson61, R.id.lesson62, R.id.lesson63, R.id.lesson64, R.id.lesson65, R.id.lesson66, R.id.lesson67},
    };
    //某节课的背景图,用于随机获取
    private int[] bg = {R.drawable.kb1, R.drawable.kb2, R.drawable.kb3, R.drawable.kb4, R.drawable.kb5, R.drawable.kb6, R.drawable.kb7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        dialog = new MaterialDialog.Builder(this).customView(R.layout.view_progress, true).build();

        //showView(0);
        initData(1, SharedPreferencesUtils.getInstance().getUserMessage().getMemberId(), "5");

    }

    void initActionBar() {

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(showTitle);
        //logo
        mActionBar.setDisplayShowHomeEnabled(false);

        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layout.gravity = Gravity.RIGHT;
        View v = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.menu_course_topbar, null);
        mActionBar.setCustomView(v, layout);
        ll_view_setweek = (LinearLayout) v.findViewById(R.id.view_setweek);
        tv_setweek = (TextView) v.findViewById(R.id.tv_setweek);
        ll_view_setweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(CourseActivity.this)
                        .title("选择当前周")
                        .items(R.array.weeks)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                //  Toast.makeText(getApplicationContext(), which + ": " + text, Toast.LENGTH_SHORT).show();
                                tv_setweek.setText("第" + text + "周");
                                if(SharedPreferencesUtils.getInstance().isLogin())
                                initData(1, SharedPreferencesUtils.getInstance().getUserMessage().getMemberId(), String.valueOf(text));
                            }
                        })
                        .negativeText("取消")
                        .show();
                //  Fc_DialogWithWheel.showCustomView(CourseActivity.this);
            }
        });
    }

    private void initData(int tag, String memberId, String week) {
        // 数据
        dialog.show();
        //http://192.168.3.12:8080/MyService/servlet/courseServlet
        DataHelper piclist = new PostDataHelper("http://115.28.131.31:81/api/student/GetCourseList",
                URLHelper.getCourseListParams(memberId, week), this, tag);
        piclist.execute();
    }

    /**
     * 初始化视图
     */
    private void initViewwithdata() {
        //这里有逻辑问题，只是简单的显示了下数据，数据并不一定是显示在正确位置
        //课程可能有重叠
        //课程可能有1节课的，2节课的，3节课的，因此这里应该改成在自定义View上显示更合理

        Course course = null;
        //循环遍历
        for (int i = 0; i < listdata.getCourseList().size(); i++) {
            course = listdata.getCourseList().get(i);//拿到当前课程
            int dayOfWeek = (course.getDayOfWeek() > 7 ? 7 : course.getDayOfWeek()) - 1;//转换为lessons数组对应的下标
            int section = course.getStartSection() / 2;//转换为lessons数组对应的下标
            Button lesson = null;
            try {
                lesson = (Button) findViewById(lessons[section][dayOfWeek]);//获得该节课的button
                lesson.setTag(course);
                int bgRes = bg[(int) (Math.random() * (bg.length - 1))];//随机获取背景色
                lesson.setBackgroundResource(bgRes);//设置背景
                lesson.setText(course.getCourseName() + "@" + course.getClassroom());//设置文本为课程名+“@”+教室
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void sucess(JSONObject response, int code) {
// 下拉刷新数据返回处理
        ResultSingleBean rb1 = (ResultSingleBean) VolleyResponseHelper
                .jsonToBean(response, 31);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                Button b = (Button) findViewById(lessons[i][j]);
                b.setTag(null);
                b.setBackgroundResource(0);//设置背景
                b.setText("");
            }
        }
        if (rb1.getRetCode() == 0) {
            dialog.dismiss();
            listdata = (CourseListBean) rb1.getRetObj();
        }
        if (listdata != null && listdata.getCourseList() != null) {
            if (listdata.getCourseList().size() == 0) {
                // showView(1);
            } else {
                //   showView(3);
                initViewwithdata();
            }
        } else {
            CustomToast.showToast("内容为空", CourseActivity.this);
        }
    }

    @Override
    public void err(String error, int code) {
        //  showView(2);
        dialog.dismiss();
        CustomToast.showToast(error, this);
    }

    public void OnClick(View v) {
        if (v.getTag() != null) {
            //// Log.e("onclick",((Course)v.getTag()).getCourseName());
            CourseDetailsActivity_.intent(this).courseBean((Course) v.getTag()).start();
        }
    }

}
