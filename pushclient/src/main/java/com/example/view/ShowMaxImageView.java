package com.example.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.example.common.CommonUtils;


/**
 * 自定义控件，用于显示宽度和ImageView相同，高度自适应的图片显示模式.
 * 除此之外，还添加了最大高度限制，若图片长度大于等于屏幕长度，则高度显示为屏幕的1/3
 */
public class ShowMaxImageView extends ImageView {

    private float mHeight = 0;


    public ShowMaxImageView(Context context) {
        super(context);
    }

    public ShowMaxImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowMaxImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        if (bm != null) {
            getHeight(bm);
        }
        super.setImageBitmap(bm);
        requestLayout();
    }


    @Override
    public void setImageDrawable(Drawable drawable) {
        if (drawable != null) {
            getHeight(drawableToBitamp(drawable));
        }
        super.setImageDrawable(drawable);
        requestLayout();

    }

    private void getHeight(Bitmap bm) {

        float bitmapWidth = bm.getWidth();
        float bitmapHeight = bm.getHeight();
        Log.e("bm.getHeight", bitmapHeight + "");
        if (bitmapWidth > 0 && bitmapHeight > 0) {
            float scaleWidth = getWidth() / bitmapWidth;
            Log.e("getWidth", getWidth() + "");

            mHeight = bitmapHeight * scaleWidth;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mHeight != 0) {
            int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
            int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
            Log.e("sizeWidth", sizeWidth + "");
            Log.e("sizeHeight", sizeHeight + "");
            int resultHeight = (int) Math.max(mHeight, sizeHeight);

            if (resultHeight >= CommonUtils.getScreenHeight((Activity) getContext())) {
                resultHeight = CommonUtils.getScreenHeight((Activity) getContext()) / 3;
            }
            Log.e("屏幕 ", CommonUtils.getScreenHeight((Activity) getContext()) + "  " + CommonUtils.getScreenWidthPX((Activity) getContext()));

            setMeasuredDimension(sizeWidth, resultHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }


    }


    private Bitmap drawableToBitamp(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }
}
