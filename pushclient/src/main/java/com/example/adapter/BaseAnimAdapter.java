package com.example.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.R;


/**
 * Adapter适配器,为Item添加动画
 *
 * @author fangc
 */
public abstract class BaseAnimAdapter extends BaseAdapter {
    private int lastPosition = -1;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.item_slide_bottom_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

}
