package com.example.fragment;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;

import com.example.activity.HomeActivity;

/**
 * 可点击 头部
 * Created by fc on 2015/1/12.
 */
public abstract class BaseFragment extends Fragment {

    protected ActionBar mActionBar;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof HomeActivity) {
          HomeActivity activity1 = ((HomeActivity) getActivity());
            mActionBar = activity1.getActionBar1();
            Log.e("fc basefragment",activity1.getClass()+" "+mActionBar);
            if(mActionBar!=null)

            mActionBar.getCustomView()
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onActionBarClick();
                            Log.e("fc click","actionbar 点击事件");

                        }
                    });
        }

    }

    /**
     * 重写该方法，可以自由的处理在MainActivity下的ActionBar的点击事件
     */
    public void onActionBarClick() {
    }

}
