package com.example.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.example.R;

import java.util.LinkedList;
import java.util.List;


/**
 * 右滑关闭 布局
 *
 * @author fc
 */
public class SwipeBackLayout extends FrameLayout {
    private static final String TAG = SwipeBackLayout.class.getSimpleName();
    private View mContentView;
    private int mTouchSlop;
    private float mScaledEdgeSlop;
    private int downX;
    private int downY;
    private int tempX;
    private Scroller mScroller;
    private int viewWidth;
    private boolean isSilding;
    private boolean isFinish;
    private boolean allowslide_flage = true;
    private Drawable mShadowDrawable;
    private Activity mActivity;


    private List<ViewPager> mViewPagers = new LinkedList<ViewPager>();
    private List<View> ignoredviews = new LinkedList<View>();

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
        mScaledEdgeSlop = ViewConfiguration.get(context).getScaledEdgeSlop();

        mShadowDrawable = getResources().getDrawable(R.drawable.shadow_left);
    }

    /**
     * 忽略 View v 上面的右滑finish事件，让事件可以传递到view 本身
     *
     * @param v
     */
    public void addIgnoredViews(View v) {
        if (v != null)
            ignoredviews.add(v);
    }

    /**
     * 返回我们touch的区域是否是view 是返回该view  否则返回null
     *
     * @param ignoreviews
     * @param ev
     * @return
     */
    private View getTouchIgnoredView(List<View> ignoreviews, MotionEvent ev) {
        if (ignoreviews == null || ignoreviews.size() == 0) {
            return null;
        }
        Rect mRect = new Rect();
        for (View v : ignoreviews) {
            v.getHitRect(mRect);

            if (mRect.contains((int) ev.getX(), (int) ev.getY())) {
                return v;
            }
        }
        return null;
    }

    public void attachToActivity(Activity activity) {
        mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.windowBackground});
        int background = a.getResourceId(0, 0);
        a.recycle();

        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        setContentView(decorChild);
        decor.addView(this);
    }

    private void setContentView(View decorChild) {
        mContentView = (View) decorChild.getParent();
    }

    /**
     * 设置是否允许滚动标志位
     *
     * @param allowslide_flage
     */
    public void setAllowslide_flage(boolean allowslide_flage) {
        this.allowslide_flage = allowslide_flage;
    }

    /**
     * 事件拦截
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //处理ViewPager冲突问题
        ViewPager mViewPager = getTouchViewPager(mViewPagers, ev);
        View ignoredview = getTouchIgnoredView(ignoredviews, ev);
        if (ignoredview != null) {
            return super.onInterceptTouchEvent(ev);
        }

        if (mViewPager != null && mViewPager.getCurrentItem() != 0) {
            return super.onInterceptTouchEvent(ev);
        }
        if (allowslide_flage) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = tempX = (int) ev.getRawX();
                    downY = (int) ev.getRawY();

                    break;
                case MotionEvent.ACTION_MOVE:
                    int moveX = (int) ev.getRawX();
                    //直邮在左侧边 滑动才能关闭
                    if (downX > mScaledEdgeSlop) {
                        return super.onInterceptTouchEvent(ev);
                    }
                    //
                    // 满足此条件屏蔽SildingFinishLayout里面子类的touch事件
                    else if (moveX - downX > mTouchSlop
                            && Math.abs((int) ev.getRawY() - downY) < mTouchSlop) {
                        return true;
                    }
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int deltaX = tempX - moveX;
                tempX = moveX;
                if (moveX - downX > mTouchSlop
                        && Math.abs((int) event.getRawY() - downY) < mTouchSlop) {
                    isSilding = true;
                }

                if (moveX - downX >= 0 && isSilding && allowslide_flage) {
                    mContentView.scrollBy(deltaX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                isSilding = false;


                if (mContentView.getScrollX() <= -viewWidth / 2) {
                    isFinish = true;
                    scrollRight();
                } else {
                    scrollOrigin();
                    isFinish = false;
                }
                break;
        }

        return true;

    }

    /**
     * 获取SwipeBackLayout里面的ViewPager的集合
     *
     * @param mViewPagers
     * @param parent
     */
    private void getAlLViewPager(List<ViewPager> mViewPagers, ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewPager) {
                mViewPagers.add((ViewPager) child);
            } else if (child instanceof ViewGroup) {
                getAlLViewPager(mViewPagers, (ViewGroup) child);
            }
        }
    }


    /**
     * 返回我们touch的ViewPager
     *
     * @param mViewPagers
     * @param ev
     * @return
     */
    private ViewPager getTouchViewPager(List<ViewPager> mViewPagers, MotionEvent ev) {
        if (mViewPagers == null || mViewPagers.size() == 0) {
            return null;
        }
        Rect mRect = new Rect();
        for (ViewPager v : mViewPagers) {
            v.getHitRect(mRect);

            if (mRect.contains((int) ev.getX(), (int) ev.getY())) {
                return v;
            }
        }
        return null;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            viewWidth = this.getWidth();

            getAlLViewPager(mViewPagers, this);
            Log.i(TAG, "ViewPager size = " + mViewPagers.size());
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mShadowDrawable != null && mContentView != null) {

            int left = mContentView.getLeft()
                    - mShadowDrawable.getIntrinsicWidth();
            int right = left + mShadowDrawable.getIntrinsicWidth();
            int top = mContentView.getTop();
            int bottom = mContentView.getBottom();

            mShadowDrawable.setBounds(left, top, right, bottom);
            mShadowDrawable.draw(canvas);
        }

    }


    /**
     * 滚动出界面
     */
    private void scrollRight() {
        final int delta = (viewWidth + mContentView.getScrollX());
        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        mScroller.startScroll(mContentView.getScrollX(), 0, -delta + 1, 0,
                Math.abs(delta));
        postInvalidate();
    }

    /**
     * 滚动到起始位置
     */
    private void scrollOrigin() {
        int delta = mContentView.getScrollX();
        mScroller.startScroll(mContentView.getScrollX(), 0, -delta, 0,
                Math.abs(delta));
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        // 调用startScroll的时候scroller.computeScrollOffset()返回true，
        if (mScroller.computeScrollOffset()) {
            mContentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();

            if (mScroller.isFinished() && isFinish) {
                mActivity.finish();
            }
        }
    }


}
