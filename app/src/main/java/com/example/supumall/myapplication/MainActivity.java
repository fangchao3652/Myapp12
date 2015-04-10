package com.example.supumall.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PloyToPolyView(this));
    }

    class PloyToPolyView extends View {


        private Bitmap mBitmap;
        private Matrix mMatrix;


        private Paint mShadowPaint;
        private Matrix mShadowGradientMatrix;
        private LinearGradient mShadowGradientShader;

        public PloyToPolyView(Context context) {
            super(context);
            Log.e("fcfc", "new  ");
            mBitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.img12);
            mMatrix = new Matrix();
            //添加黑色渐变
            mShadowPaint = new Paint();
            mShadowPaint.setStyle(Paint.Style.FILL);
            mShadowGradientShader = new LinearGradient(0, 0, 1f, 0,
                    Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
            mShadowPaint.setShader(mShadowGradientShader);

            mShadowGradientMatrix=new Matrix();
            mShadowGradientMatrix.setScale(mBitmap.getWidth(),1);
            mShadowGradientShader.setLocalMatrix(mShadowGradientMatrix);
            mShadowPaint.setAlpha((int)0.9*255);
          /*  float[] src = {0, 0,//
                    mBitmap.getWidth(), 0,//
                    mBitmap.getWidth(), mBitmap.getHeight(),//
                    0, mBitmap.getHeight()};
            float[] dst = {0, 0,//
                    mBitmap.getWidth(), 100,//
                    mBitmap.getWidth(), mBitmap.getHeight() - 100,//
                    0, mBitmap.getHeight()};
            mMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);*/
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float[] src = {0, 0,//
                    mBitmap.getWidth(), 0,//
                    mBitmap.getWidth(), mBitmap.getHeight(),//
                    0, mBitmap.getHeight()};
            float[] dst = {0, 0,//
                    mBitmap.getWidth(), 100,//
                    mBitmap.getWidth(), mBitmap.getHeight() - 100,//
                    0, mBitmap.getHeight()};
            mMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);
            canvas.concat(mMatrix);
            canvas.drawBitmap(mBitmap, 0,0, null);
               canvas .drawRect(0,0,mBitmap.getWidth(),mBitmap.getHeight(),mShadowPaint);
            Log.e("fcfc", "draw");

        }
    }


}
