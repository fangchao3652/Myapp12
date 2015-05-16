package com.example.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.Bean.BannerBean;
import com.example.Bean.BaseBean;
import com.example.fragment.BaseFragment;
import com.example.fragment.FragmentBanner_;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页推荐Banner的viewpager的Adapter
 *
 * @author fc
 */
public class BannerAdapter extends FragmentStatePagerAdapter {

    private List<BannerBean> data;
    private List<BaseFragment> fragments;

    public BannerAdapter(List<BannerBean> data, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.data = data;
        fragments = new ArrayList<>();
        for (int i = 0; i < data.size() + 2; i++) {
            int position = i;
            if (position == 0) {
                position = getCount() - 2;
            } else if (position == getCount() - 1) {
                position = 1;
            }
            position -= 1;
            fragments.add(FragmentBanner_.builder()
                    .bean(getItemData(position)).build());
        }
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size() + 2;
        }
        return 0;
    }

    /**
     * 获取每一条的数据bean
     *
     * @param position
     * @return
     */
    private BannerBean getItemData(int position) {
        if (data != null) {
            return (BannerBean) data.get(position);
        }
        return null;
    }


}
