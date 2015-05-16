package com.example.fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.example.Bean.BannerBean;
import com.example.R;
import com.example.common.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * 首页推荐Banner图片Fragment
 *
 * @author fc
 */
@EFragment(R.layout.fragment_banner)
public class FragmentBanner extends BaseFragment {

    @FragmentArg
    BannerBean bean;

    @ViewById(R.id.banner_item_img)
    SimpleDraweeView imgView;
    @ViewById(R.id.banner_item_img_no)
    ImageView imgNoV;

    @AfterViews
    void initView() {
        // 给view设置值

        if (3 == SharedPreferencesUtils.getInstance().getImgQuerity()) {
            // 不显示网络图片
            imgView.setVisibility(View.GONE);
            imgNoV.setVisibility(View.VISIBLE);
        } else {
            imgView.setVisibility(View.VISIBLE);
            imgNoV.setVisibility(View.GONE);
            if (SharedPreferencesUtils.getInstance().isNightTheme()) {
                imgView.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            } else {
                imgView.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            }
            imgView.setImageURI(Uri.parse(bean.getImageUrl()));
        }
    }

   /* @Click({R.id.banner_item_img, R.id.banner_item_img_no})
    void click(View v) {
        // 类型 Product：商品页 List：商品列表页 Search：搜索页 Activity：活动页
        if (getType("Product")) {
            // 跳转商品详情页面
            cc.android.supu.activity.GoodsDetailActivity_.intent(this)
                    .goodsSN(bean.getLinkData()).start();
        } else if (getType("List")) {
            String[] s = bean.getLinkData().split("-");
            // 跳转商品列表页面
            GoodsListActivity_.intent(this).mbrandId(s.length >= 2 ? s[1] : "").
                    mcategoryId(s.length >= 1 ? s[0] : "").start();
        } else if (getType("Search")) {
            // 跳转商品列表页面
            GoodsListActivity_.intent(this).msearchKey(bean.getLinkData()).start();
        } else if (getType("Activity")) {
            // 跳转活动详情页面
            cc.android.supu.activity.ActActivity_.intent(this)
                    .ActivityId(bean.getLinkData()).start();
        } else if (getType("Falsh")) {
            String[] s = bean.getLinkData().split("-");
            BoutiqueSaleBean boutiqueSaleBean = new BoutiqueSaleBean();
            boutiqueSaleBean.setFlashSaleName(s.length >= 2 ? s[0] : "");
            boutiqueSaleBean.setFlashSaleId(s.length >= 2 ? s[1] : "");
            cc.android.supu.activity.BoutiqueSalesActivity_.intent(this).boutiqueSaleBean(boutiqueSaleBean).start();
        }
    }*/



}
