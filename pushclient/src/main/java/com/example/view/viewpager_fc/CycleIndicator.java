package com.example.view.viewpager_fc;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;


import com.example.R;

import static android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * Viewpager 底部的指示view
 */
public class CycleIndicator extends LinearLayout implements OnPageChangeListener {

    private final static int DEFAULT_INDICATOR_WIDTH = 5;
    private ViewPager mViewpager;
    private OnPageChangeListener mViewPagerOnPageChangeListener;
    private int mIndicatorMargin;
    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int mAnimatorResId = R.animator.scale_with_alpha;
    private int mAnimatorReverseResId = -1;
    private int mIndicatorBackground = R.drawable.white_radius;
    private int mCurrentPosition = 0;
    private Animator mAnimationOut;
    private Animator mAnimationIn;

    public CycleIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public CycleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        handleTypedArray(context, attrs);
        mAnimationOut = AnimatorInflater.loadAnimator(context, mAnimatorResId);
        if (mAnimatorReverseResId == -1) {
            mAnimationIn = AnimatorInflater.loadAnimator(context, mAnimatorResId);
            mAnimationIn.setInterpolator(new ReverseInterpolator());
        } else {
            mAnimationIn = AnimatorInflater.loadAnimator(context, mAnimatorReverseResId);
        }
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray =
                    context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);
            mIndicatorWidth =
                    typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
            mIndicatorHeight =
                    typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_height, -1);
            mIndicatorMargin =
                    typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_margin, -1);

            mAnimatorResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator,
                    R.animator.scale_with_alpha);
            mAnimatorReverseResId =
                    typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator_reverse, -1);
            mIndicatorBackground = typedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable,
                    R.drawable.white_radius);
            typedArray.recycle();
        }
        mIndicatorWidth =
                (mIndicatorWidth == -1) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorWidth;
        mIndicatorHeight =
                (mIndicatorHeight == -1) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorHeight;
        mIndicatorMargin =
                (mIndicatorMargin == -1) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorMargin;
    }

    public void setViewPager(ViewPager viewPager) {
        mViewpager = viewPager;
        createIndicators(viewPager);
        mViewpager.setOnPageChangeListener(this);
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        if (mViewpager == null) {
            throw new NullPointerException("can not find Viewpager , setViewPager first");
        }
        mViewPagerOnPageChangeListener = onPageChangeListener;
        mViewpager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        if (mViewPagerOnPageChangeListener != null) {
            mViewPagerOnPageChangeListener.onPageScrolled(position, positionOffset,
                    positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mViewPagerOnPageChangeListener != null) {
            mViewPagerOnPageChangeListener.onPageSelected(position);
        }

        mAnimationIn.setTarget(getChildAt(mCurrentPosition));
        mAnimationIn.start();
        if (position == 0) {
            position = mViewpager.getAdapter().getCount() - 3;
        } else if (position == mViewpager.getAdapter().getCount() - 1) {
            position = 0;
        } else {
            position -= 1;
        }
        mAnimationOut.setTarget(getChildAt(position));
        mAnimationOut.start();
        mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mViewPagerOnPageChangeListener != null) {
            mViewPagerOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    private void createIndicators(ViewPager viewPager) {
        removeAllViews();
        int count = viewPager.getAdapter().getCount();
        if (count <= 0) {
            return;
        }

        for (int i = 0; i < count - 2; i++) {
            View Indicator = new View(getContext());
            Indicator.setBackgroundResource(mIndicatorBackground);
            addView(Indicator, mIndicatorWidth, mIndicatorHeight);
            LayoutParams lp = (LayoutParams) Indicator.getLayoutParams();
            lp.leftMargin = mIndicatorMargin;
            lp.rightMargin = mIndicatorMargin;
            Indicator.setLayoutParams(lp);
            mAnimationOut.setTarget(Indicator);
            mAnimationOut.start();
        }

        mAnimationOut.setTarget(getChildAt(mCurrentPosition));
        mAnimationOut.start();
    }

    private class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
