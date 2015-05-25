package com.example.view.viewpager_fc;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;

import java.lang.reflect.Field;

/**
 * @author fc
 *         <p/>
 *         循环滑动的ViewPager
 */
public class CycleViewPager extends ViewPager {

    public static final int SCROLL_WHAT = 0;
    private boolean isAutoScroll = false;
    private boolean isStopByTouch = false;
    public static final int DEFAULT_INTERVAL = 5000;
    private long interval = DEFAULT_INTERVAL;
    private Handler handler;

    public CycleViewPager(Context context) {
        this(context, null);
    }

    public CycleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOnPageChangeListener(null);
        controlViewPagerSpeed();
        handler = new MyHandler();
    }

    @Override
    public void setAdapter(PagerAdapter arg0) {
        super.setAdapter(arg0);
        setCurrentItem(1);
        startAutoScroll();
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        super.setOnPageChangeListener(new CycleOnPageChangeListener(listener));
    }

    private class CycleOnPageChangeListener implements OnPageChangeListener {

        private OnPageChangeListener listener;
        private int position;

        public CycleOnPageChangeListener(OnPageChangeListener listener) {
            this.listener = listener;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (null != listener) {
                listener.onPageScrollStateChanged(arg0);
            }
            if (arg0 == ViewPager.SCROLL_STATE_IDLE) {
                if (position == getAdapter().getCount() - 1) {
                    setCurrentItem(1, false);
                } else if (position == 0) {
                    setCurrentItem(getAdapter().getCount() - 2, false);
                }
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if (null != listener) {
                listener.onPageScrolled(arg0, arg1, arg2);
            }
        }

        @Override
        public void onPageSelected(int arg0) {
            position = arg0;
            if (null != listener) {
                listener.onPageSelected(arg0);
            }
        }
    }

    public void startAutoScroll() {
        stopAutoScroll();
        isAutoScroll = true;
        sendScrollMessage(interval);
    }

    public void stopAutoScroll() {
        isAutoScroll = false;
        handler.removeMessages(SCROLL_WHAT);
    }

    private void sendScrollMessage(long delayTimeInMills) {
        handler.removeMessages(SCROLL_WHAT);
        handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SCROLL_WHAT:
                    if(getAdapter()!=null)
                    if (getAdapter().getCount() > 1) {
                        if(getCurrentItem() == getAdapter().getCount()-1){
                            setCurrentItem(1, true);
                        }else {
                            setCurrentItem(getCurrentItem() + 1, true);
                        }
                    }
                    sendScrollMessage(interval);
                    break;
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        if ((action == MotionEvent.ACTION_DOWN) && isAutoScroll) {
            isStopByTouch = true;
            stopAutoScroll();
        } else if (ev.getAction() == MotionEvent.ACTION_UP && isStopByTouch) {
            startAutoScroll();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDetachedFromWindow() {
        stopAutoScroll();
        super.onDetachedFromWindow();
    }

    FixedSpeedScroller mScroller = null;

    private void controlViewPagerSpeed() {
        try {
            Field mField;
            mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new FixedSpeedScroller(
                    getContext(),
                    new AccelerateInterpolator());
            mScroller.setmDuration(400); // 2000ms
            mField.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
