package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * 重写Scrollview，用来获取滑动事件  | 滑速差效果
 *
 * @author fc
 */
public class CustomScrollView extends ScrollView {

    private View mView;
    private float touchY;

    private static final int MAX_SCROLL_HEIGHT = 200;// 最大滑动距离
    private static final float SCROLL_RATIO = 0.4f;// 阻尼系数,越小阻力就越大

    private OnScrollListener onScrollListener;

    public CustomScrollView(Context context) {
        this(context, null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置滚动接口
     *
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.onScroll(t);
        }
    }

    /**
     * 滚动的回调接口
     */
    public interface OnScrollListener {
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         *
         * @param scrollY 、
         */
        public void onScroll(int scrollY);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            this.mView = getChildAt(0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
            touchY = arg0.getY();
        }
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mView == null) {
            return super.onTouchEvent(ev);
        } else {
            commonOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    private int scrollMoveY = 0;

    private void commonOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                scrollMoveY = 0;
                computeScroll();
                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = ev.getY();
                int deltaY = (int) (touchY - nowY);
                touchY = nowY;
                if (isNeedMove()) {
                    int offset = mView.getScrollY();
                    if (offset < MAX_SCROLL_HEIGHT && offset > -MAX_SCROLL_HEIGHT) {
                        scrollMoveY += (int) (deltaY * SCROLL_RATIO);
                    }
                    if (onScrollListener != null) {
                        if (scrollMoveY < 0) {
                            onScrollListener.onScroll(scrollMoveY);
                        }
                    }
                }

                break;
            default:
                break;
        }
    }

    private boolean isNeedMove() {
        int offset = mView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

}
