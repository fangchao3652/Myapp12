package com.example.fragment;

import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.Bean.ResultSingleBean;
import com.example.Bean.UserBean;
import com.example.R;
import com.example.activity.LoginActivity_;
import com.example.activity.SettingActivity_;
import com.example.common.CommonUtils;
import com.example.common.CustomToast;
import com.example.common.SharedPreferencesUtils;
import com.example.common.StringUtils;
import com.example.data.DataHelper;
import com.example.data.PostDataHelper;
import com.example.data.URLHelper;
import com.example.data.VolleyResponseHelper;
import com.facebook.drawee.view.SimpleDraweeView;
import com.readystatesoftware.viewbadger.BadgeView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

/**
 * Created by Xuzj on 2015/1/22.
 * 左滑个人中心
 */
@EFragment(R.layout.fragment_menu_right)
public class FragmentRight  extends Fragment implements DataHelper.DataListener {

    @ViewById(R.id.menu_right_fragment_personYes)
    LinearLayout personYesV;//登录后
    @ViewById(R.id.menu_right_fragment_personNo)
    LinearLayout personNoV;//未登录
    @ViewById(R.id.view_login)
    LinearLayout view_login;//点击头像登录
    @ViewById(R.id.view_register)
    RelativeLayout view_register;//注册
    @ViewById(R.id.menu_img_head)
    SimpleDraweeView headImgV;//个人头像
    @ViewById(R.id.menu_right_fragment_personName)
    TextView personNameV;//会员昵称
    @ViewById(R.id.menu_right_fragment_setting)
    Button btn_setting;//设置
    @ViewById(R.id.menu_right_fragment_more)
    Button btn_more;//更多
    @ViewById(R.id.menu_right_fragment_versionName)
    TextView versionNameV;

    BadgeView payCountBadge;// 待付款 数量
    BadgeView noReceiptBadge;// 待收货 数量
    BadgeView eaidBadge;// 待评价 数量
    BadgeView cartBadge;// 购物车 数量

    // 数据
    DataHelper mDataHelper;

    @AfterViews
    void initview() {
        versionNameV.setText(CommonUtils.getAppVersionName());
    }

    /**
     * 初始化person显示区域
     */
    private void initPerson() {
        if (SharedPreferencesUtils.getInstance().isLogin()) {
            // 登陆
            UserBean bean = SharedPreferencesUtils.getInstance()
                    .getUserMessage();
            personNoV.setVisibility(View.GONE);
            personYesV.setVisibility(View.VISIBLE);
            // 赋值
            //fc
            bean.setImageUrl("http://img4.imgtn.bdimg.com/it/u=241944428,4078451778&fm=21&gp=0.jpg");
            if (StringUtils.isBlank(bean.getImageUrl())) {
                headImgV.setBackgroundResource(R.drawable.face_big_normal);
                headImgV.setImageResource(R.drawable.face_big_normal);
            } else {
                // 给view设置值
                headImgV.setImageURI(Uri.parse(bean.getImageUrl()));
            }
            personNameV.setText(bean.getSName());


            // 数字动画
            TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
            anim.setInterpolator(new BounceInterpolator());
            anim.setDuration(1000);
/*

            // 避免反复添加数字view覆盖问题所以加此判断
            if (payCountBadge != null) {
                payCountBadge.setText(bean.getPaycount());
            } else {
                payCountBadge = getBadgeV(payCountV, bean.getPaycount());
            }
            if ("0".equals(bean.getPaycount())) {
                payCountBadge.setText("");
                payCountBadge.setVisibility(View.GONE);
            } else {
                payCountBadge.setVisibility(View.VISIBLE);
                payCountBadge.show(anim);
            }
            if (noReceiptBadge != null) {
                noReceiptBadge.setText(bean.getNoReceipt());
            } else {
                noReceiptBadge = getBadgeV(noReceiptV, bean.getNoReceipt());
            }
            if ("0".equals(bean.getNoReceipt())) {
                noReceiptBadge.setText("");
                noReceiptBadge.setVisibility(View.GONE);
            } else {
                noReceiptBadge.setVisibility(View.VISIBLE);
                noReceiptBadge.show(anim);
            }

            if (eaidBadge != null) {
                eaidBadge.setText(bean.getEaid());
            } else {
                eaidBadge = getBadgeV(eaidV, bean.getEaid());
            }
            if ("0".equals(bean.getEaid())) {
                eaidBadge.setText("");
                eaidBadge.setVisibility(View.GONE);
            } else {
                eaidBadge.setVisibility(View.VISIBLE);
                eaidBadge.show(anim);
            }

            // 购物车数量
            if (cartBadge != null)
                cartBadge.setText(bean.getCartCount());
            else
                cartBadge = getBadgeView(shoppingCartBtnV, bean.getCartCount());
            if ("0".equals(bean.getCartCount())) {
                cartBadge.setText("");
                cartBadge.setVisibility(View.GONE);
            } else {
                cartBadge.setVisibility(View.VISIBLE);
                cartBadge.show(anim);
            }
*/

        } else {
            // 未登录
            personNoV.setVisibility(View.VISIBLE);
            personYesV.setVisibility(View.GONE);
        }
    }

    @Click({R.id.view_login, R.id.view_register, R.id.menu_img_head,
            R.id.menu_right_fragment_setting, R.id.menu_right_fragment_more})
    void click(View view) {
        switch (view.getId()) {
            case R.id.view_login://登录
                LoginActivity_.intent(getActivity()).start();
                break;
            case R.id.view_register://注册
                //  RegisterActivity_.intent(getActivity()).start();
                break;
            case R.id.menu_img_head://个人中心
                //  UserCenterActivity_.intent(getActivity()).start();

            case R.id.menu_right_fragment_setting://系统设置
                  SettingActivity_.intent(getActivity()).start();
                break;
            case R.id.menu_right_fragment_more://更多
                // MoreActivity_.intent(getActivity()).start();
                break;
        }
    }

    /**
     * 获取数字view
     *
     * @param v
     * @param str
     * @return
     */
    private BadgeView getBadgeV(View v, String str) {
        BadgeView mBadgeView = new BadgeView(getActivity(), v);
        mBadgeView.setText(str);
        mBadgeView.setBadgeMargin(20, 0);
        mBadgeView.setBadgeBackgroundColor(Color.parseColor("#e5004a"));
        return mBadgeView;
    }

    /**
     * 获取数字view
     *
     * @param v
     * @param str
     * @return
     */
    private BadgeView getBadgeView(View v, String str) {
        BadgeView mBadgeView = new BadgeView(getActivity(), v);
        mBadgeView.setText(str);
        mBadgeView.setBadgeMargin(20, CommonUtils.dipToPixels(12));
        mBadgeView.setBadgeBackgroundColor(Color.parseColor("#e5004a"));
        return mBadgeView;
    }


    @Override
    public void onResume() {
        super.onResume();
        initPerson();
        initData();
        refreshLocation();
    }

    /**
     * 获取用户信息
     */
    private void initData() {
        if (SharedPreferencesUtils.getInstance().isLogin()) {
            mDataHelper = new PostDataHelper(URLHelper.getURL(
                    "", URLHelper.M_LOGIN),
                    URLHelper.getLoginParams(SharedPreferencesUtils.getInstance().getUserMessage().getMemberId().trim(), SharedPreferencesUtils.getInstance().getUserMessage().getMemberPwd(), "a123213121313213131"), this, 0);
            mDataHelper.execute();
        }
    }

    @Override
    public void sucess(JSONObject response, int code) {
        switch (code) {
            case 0:
                ResultSingleBean rb = (ResultSingleBean) VolleyResponseHelper
                        .jsonToBean(response, 4);
                if (rb.getRetCode() == 0) {
                    // 登陆成功
                    UserBean bean = (UserBean) rb.getRetObj();
                    SharedPreferencesUtils.getInstance().editUserMessage(bean);
                    initNumber();
                } else {
                    CustomToast.showToast(rb.getRetMessage(), getActivity());
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void err(String error, int code) {
        CustomToast.showToast(error, getActivity());
    }

    private void initNumber() {
        UserBean bean = SharedPreferencesUtils.getInstance().getUserMessage();

       /* // 避免反复添加数字view覆盖问题所以加此判断
        if (payCountBadge != null) {
            if (!"0".equals(bean.getPaycount())) {
                payCountBadge.setText(bean.getPaycount());
                payCountBadge.setVisibility(View.VISIBLE);
            } else {
                payCountBadge.setText("");
                payCountBadge.setVisibility(View.GONE);
            }
        }
        if (noReceiptBadge != null) {
            if (!"0".equals(bean.getNoReceipt())) {
                noReceiptBadge.setText(bean.getNoReceipt());
                noReceiptBadge.setVisibility(View.VISIBLE);
            } else {
                noReceiptBadge.setText("");
                noReceiptBadge.setVisibility(View.GONE);
            }
        }

        if (eaidBadge != null) {
            if (!"0".equals(bean.getEaid())) {
                eaidBadge.setText(bean.getEaid());
                eaidBadge.setVisibility(View.VISIBLE);
            } else {
                eaidBadge.setText("");
                eaidBadge.setVisibility(View.GONE);
            }
        }

        // 购物车数量
        if (cartBadge != null) {
            if (!"0".equals(bean.getCartCount())) {
                cartBadge.setText(bean.getCartCount());
                cartBadge.setVisibility(View.VISIBLE);
            } else {
                cartBadge.setText("");
                cartBadge.setVisibility(View.GONE);
            }
        }*/
    }

    /**
     * 刷新版本号位置
     */
    private void refreshLocation() {
        if (ViewConfiguration.get(getActivity()).hasPermanentMenuKey()) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) versionNameV.getLayoutParams();
            lp.setMargins(0, 0, CommonUtils.dipToPixels(8), CommonUtils.dipToPixels(8));
            versionNameV.setLayoutParams(lp);
        } else {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) versionNameV.getLayoutParams();
            lp.setMargins(0, 0, CommonUtils.dipToPixels(8), CommonUtils.dipToPixels(50));
            versionNameV.setLayoutParams(lp);
        }
    }
}
