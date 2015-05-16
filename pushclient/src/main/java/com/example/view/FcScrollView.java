package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by fc on 2015/5/16.
 */
public class FcScrollView extends ScrollView {
    private ScrollviewListener scrollViewListener = null;

    public FcScrollView(Context context) {
        super(context);
    }

    public FcScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FcScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollViewListener(ScrollviewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public interface ScrollviewListener {
        void onScrollChanged(FcScrollView scrollView, int x, int y, int oldx, int oldy);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
       // super.onScrollChanged(l, t, oldl, oldt);
        if(scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}
