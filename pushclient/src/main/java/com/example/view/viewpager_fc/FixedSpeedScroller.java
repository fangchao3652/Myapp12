package com.example.view.viewpager_fc;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * @author Xuzj
 *         <p/>
 *         自定义ViewPager滑动速度
 */
public class FixedSpeedScroller extends Scroller {

    private int mDuration = 400; // default time is 1500ms

    public FixedSpeedScroller(Context context) {
        super(context);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    /**
     * set animation time
     *
     * @param time
     */
    public void setmDuration(int time) {
        mDuration = time;
    }

    /**
     * get current animation time
     *
     * @return
     */
    public int getmDuration() {
        return mDuration;
    }
}
