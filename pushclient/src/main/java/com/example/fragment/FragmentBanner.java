package com.example.fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.example.Bean.BannerBean;
import com.example.R;
import com.example.activity.NewsDetailsActivity_;
import com.example.common.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
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
            if(bean.getImageUrl()!=null)
            imgView.setImageURI(Uri.parse(bean.getImageUrl()));
        }
    }

    @Click({R.id.banner_item_img, R.id.banner_item_img_no})
    void click(View v) {
        NewsDetailsActivity_.intent(getActivity()).id(String.valueOf(bean.getId())).start();
    }



}
