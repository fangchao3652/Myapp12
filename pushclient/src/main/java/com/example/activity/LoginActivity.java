package com.example.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.Bean.ResultSingleBean;
import com.example.Bean.UserBean;
import com.example.R;
import com.example.common.CommonUtils;
import com.example.common.CustomToast;
import com.example.common.SharedPreferencesUtils;
import com.example.data.DataHelper;
import com.example.data.PostDataHelper;
import com.example.data.URLHelper;
import com.example.data.VolleyResponseHelper;
import com.example.fc.activity.MqttService;
import com.example.view.ProgressDialog;
import com.opensky.supu.materialedittext.MaterialEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

/**
 * 登录activity
 * Created by zhangxinhao on 2015/2/3.
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActionBarActivity implements DataHelper.DataListener {
    String account = "";
    String password = "";
    @ViewById(R.id.login_account)//用户名
            MaterialEditText accountEt;
    @ViewById(R.id.login_pwd)//密码
            MaterialEditText pwdEt;
    @ViewById(R.id.button_login)//登录
            Button button_login;
    @ViewById(R.id.cb_login_red)//记住密码
            CheckBox cb;

    @ViewById(R.id.login_eye)
    ImageView login_eye;
    Boolean showpwd = true;
    ProgressDialog materialDialog;

    @AfterViews
    void initView() {
        cb.setChecked(SharedPreferencesUtils.getInstance().getIsSavePw());
        UserBean i=SharedPreferencesUtils.getInstance().getUserMessage();
        accountEt.setText(SharedPreferencesUtils.getInstance().getUserMessage()
                .getMemberId());
      if (SharedPreferencesUtils.getInstance().getIsSavePw()) {
            // 保存密码
            pwdEt.setText(SharedPreferencesUtils.getInstance().getUserMessage()
                    .getMemberPwd());
        }
        materialDialog = ProgressDialog.createDialog(this);
    }


    @Click({R.id.button_login, R.id.login_eye})
    void onclick(View v) {
        switch (v.getId()) {

            case R.id.button_login:
                if (checkData()) {
                    collapseSoftInputMethod();
                    setprogressdialogEnable(true);
                    materialDialog.setMessage(getResources().getString(R.string.activity_login_logining));
                    materialDialog.show();
                    DataHelper topgoods = new PostDataHelper(URLHelper.getURL(
                            "", URLHelper.M_LOGIN),
                            URLHelper.getLoginParams(account.trim(), password.trim(), CommonUtils.getDeviceId()), this, 0);
                    topgoods.execute();
                }
                break;
            case R.id.login_eye:
                if (showpwd) {
                    login_eye.setImageResource(R.drawable.login_eye);
                    showpwd = !showpwd;
                    pwdEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    pwdEt.setSelection(pwdEt.getText().toString().length());
                } else {
                    login_eye.setImageResource(R.drawable.login_eye_press);
                    showpwd = !showpwd;
                    pwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pwdEt.setSelection(pwdEt.getText().toString().length());
                }
                break;
        }
    }

    @OnActivityResult(value = 100)
    void onActivity(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            finish();
        }
    }

    @CheckedChange(R.id.cb_login_red)
    protected void CheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferencesUtils.getInstance().editIsSavePw(isChecked);
    }

    /**
     * 检查数据的合法性
     *
     * @return
     */
    private boolean checkData() {
        account = accountEt.getText().toString();
        password = pwdEt.getText().toString();
        if (account.equals("")) {
            accountEt.setError(getResources().getString(R.string.activity_login_empty));
            return false;
        }
        if (password.equals("")) {
            pwdEt.setError(getResources().getString(R.string.activity_login_emptypty_pwd));
            return false;
        }
        return true;
    }

    @Override
    public void sucess(JSONObject response, int code) {
        switch (code) {
            case 0:
                //proDialog.dismiss();
                ResultSingleBean rb = (ResultSingleBean) VolleyResponseHelper
                        .jsonToBean(response, 4);
                if (rb.getRetCode() == 0) {
                    // 登陆成功
                    UserBean bean = (UserBean) rb.getRetObj();
                     bean.setMemberPwd(password);

                    SharedPreferencesUtils.getInstance().editUserMessage(bean);
                    setResult(RESULT_OK);
                    setprogressdialogEnable(false);
                    if (materialDialog != null)
                        materialDialog.dismiss();
                    //CustomToast.showToast("登录成功", this);
                    MqttService.AddTopic(bean.getMemberId());
                    MqttService.actionStop(this);
                    //1s后重新启动
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("login","重新启动"+"   ");
                            for(int i=0;i<MqttService.getTopicFilters().length;i++){
                                Log.e("cq",MqttService.getTopicFilters()[i]);
                            }
                            MqttService.actionStart(LoginActivity.this);
                        }
                    }, 1000);
                    finish();
                } else {
                    CustomToast.showToast(rb.getRetMessage(), this);
                    setprogressdialogEnable(false);
                    if (materialDialog != null)
                        materialDialog.dismiss();
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void err(String error, int code) {
        switch (code) {
            case 0:
                CustomToast.showToast(error, this);
                setprogressdialogEnable(false);
                if (materialDialog != null)
                    materialDialog.dismiss();
                break;

            default:
                break;
        }
    }


}