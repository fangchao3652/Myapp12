package com.example.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Bean.FreshNews;
import com.example.R;
import com.example.activity.FreshNewsDetailActivity_;
import com.example.view.ShowMaxImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.util.ArrayList;

/**
 * Created by fc on 2015/4/28.
 */
public class FragmentFreshAdapter extends FooterAdapter {

    Activity activity;
    private ArrayList<FreshNews> freshNewses;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public FragmentFreshAdapter(ArrayList<FreshNews> freshNewses, ImageLoader imageLoader, DisplayImageOptions options, Activity activity) {
        this.options = options;
        this.activity = activity;
        this.freshNewses = freshNewses;

        this.imageLoader = imageLoader;
    }

    @Override
    public boolean useFooter() {
        return isFooterShow();
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentItemViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fresh_news, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindContentItemView(RecyclerView.ViewHolder holder1, final int position) {
        final ViewHolder holder = (ViewHolder) holder1;
        final FreshNews freshNews = freshNewses.get(position);
        holder.progressBar.setProgress(0);
        holder.progressBar.setVisibility(View.VISIBLE);
        imageLoader.displayImage(freshNews.getCustomFields().getThumb_m(), holder.img, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                holder.progressBar.setVisibility(View.GONE);
                Toast.makeText(activity.getApplicationContext(), "加载失败" + failReason.getType().name(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                holder.progressBar.setVisibility(View.GONE);

            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {

                holder.progressBar.setProgress((int) (current * 100f / total));
            }
        });


        holder.tv_title.setText(freshNews.getTitle());
        holder.tv_info.setText(freshNews.getAuthor().getName() + "@" + freshNews.getTags()
                .getTitle());

        holder.tv_views.setText("浏览" + freshNews.getCustomFields().getViews() + "次");

        holder.card_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              //  Intent intent = new Intent(activity, FreshNewsDetailActivity.class);
//                intent.putExtra("FreshNews", freshNewses);
//                intent.putExtra("position", position);
                //        activity.startActivity(intent);
                FreshNewsDetailActivity_.intent(activity).position(position).FreshNewsList(freshNewses).start();
            }
        });

    }

    @Override
    public int getContentItemCount() {
        return freshNewses.size();
    }

    @Override
    public int getContentItemType(int position) {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_info;
        private TextView tv_views;
        //		private ImageView img;
        private ShowMaxImageView img;
        private CardView card_bg;
        private ProgressBar progressBar;

        public ViewHolder(View contentView) {
            super(contentView);
            progressBar = (ProgressBar) contentView.findViewById(R.id.progress);

            tv_title = (TextView) contentView.findViewById(R.id.tv_title);
            tv_info = (TextView) contentView.findViewById(R.id.tv_info);
            tv_views = (TextView) contentView.findViewById(R.id.tv_views);
            img = (ShowMaxImageView) contentView.findViewById(R.id.img);
            card_bg = (CardView) contentView.findViewById(R.id.card_bg);
        }
    }
}
