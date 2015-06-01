package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Bean.BaseBean;
import com.example.Bean.NewBaseBean;
import com.example.Bean.NewsListBean;
import com.example.R;
import com.example.common.CustomToast;
import com.example.common.SharedPreferencesUtils;
import com.example.common.StringUtils;
import com.example.view.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.io.File;

/**
 * Created by fc on 2015/4/27.
 */
public class FramgentNewsAdapter extends FooterAdapter {
    //是否是大图模式
    private static boolean isLargeMode;
    BaseBean data;// 元数据
    private OnItemClickListener listener;
    private Context mContext;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public FramgentNewsAdapter(BaseBean data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                .showImageOnLoading(R.drawable.wait)
                .showImageOnFail(R.drawable.wait)
                .build();
        isLargeMode = SharedPreferencesUtils.getInstance().getIslarge();
    }

    @Override
    public boolean useFooter() {
        return isFooterShow();
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentItemViewHolder(ViewGroup parent, int viewType) {


        RecyclerView.ViewHolder vh = null;
        if (isLargeMode) {//大图模式


            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newslists_large, parent, false);
            vh = new ContentViewHolderLarge(v);

        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newslist, parent, false);
            vh = new ContentViewHolder(v);
        }


        return vh;
    }

    @Override
    public void onBindContentItemView(final RecyclerView.ViewHolder holder, final int position) {

        if (isLargeMode) {
            final ContentViewHolderLarge vh = (ContentViewHolderLarge) holder;
            if (SharedPreferencesUtils.getInstance().getImgQuerity() == 3) {

            } else
                imageLoader.displayImage(getItem(position).getImageUrl(), vh.img, options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                        vh.progressBar.setProgress(6);
                        vh.progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                        vh.progressBar.setVisibility(View.GONE);
                        Toast.makeText(mContext.getApplicationContext(), "加载失败" + failReason.getType().name(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        vh.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {
                        vh.progressBar.setVisibility(View.GONE);

                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {

                        vh.progressBar.setProgress((int) (current * 100f / total));
                    }
                });
            vh.tv_title.setText(getItem(position).getTitle());
            vh.tv_info.setText(getItem(position).getSource());
            vh.tv_time.setText(getItem(position).getPublishTime());//fc

            vh.tv_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = getItem(position).getImageUrl();
                    if (!StringUtils.isBlank(url)) {
                        File cachefil = ImageLoader.getInstance().getDiskCache().get(url);
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        if (cachefil.exists() && cachefil != null) {
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachefil));
                        }
                        intent.putExtra(Intent.EXTRA_TEXT, "分享自" + mContext.getResources().getString(R
                                .string.app_name) + getItem(position).getTitle());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(Intent.createChooser(intent, mContext.getResources().getString(R
                                .string.app_name)));
                    } else {

                    CustomToast.showToast("此条目暂不支持此功能", mContext);
                    }
                }

            });


        } else {
            ContentViewHolder vh = (ContentViewHolder) holder;
            vh.Tv_title.setText(getItem(position).getTitle());//fc
            vh.Tv_source.setText(getItem(position).getSource());//fc
            vh.Tv_time.setText(getItem(position).getPublishTime());//fc
            if (getItem(position).getImageUrl() != null && SharedPreferencesUtils.getInstance().getImgQuerity() != 3)
                vh.item_img.setImageURI(Uri.parse(getItem(position).getImageUrl()));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, position);
                }
            }
        });

    }

    @Override
    public int getContentItemCount() {
        if (data instanceof NewsListBean) {
            NewsListBean bean = (NewsListBean) data;
            return bean.getNewsList().size();
        } else {
            return 0;
        }
    }

    @Override
    public int getContentItemType(int position) {
        return 0;
    }

    public NewBaseBean getItem(int position) {
        if (data instanceof NewsListBean) {
            NewsListBean bean = (NewsListBean) data;
            return bean.getNewsList().get(position);
        } else
            return null;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * holder
     */
    private class ContentViewHolder extends RecyclerView.ViewHolder {
        public TextView Tv_title;
        public TextView Tv_source;
        public TextView Tv_time;
        public SimpleDraweeView item_img;

        public ContentViewHolder(View itemView) {
            super(itemView);
            Tv_source = (TextView) itemView.findViewById(R.id.tv_source);
            Tv_time = (TextView) itemView.findViewById(R.id.tv_publishtime);
            Tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            item_img = (SimpleDraweeView) itemView.findViewById(R.id.item_img);
        }
    }

    /**
     * 大图模式的holder
     */
    private class ContentViewHolderLarge extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_info;
        private TextView tv_time;
        private TextView tv_share;
        private ImageView img;
        private CardView card;
        private ProgressBar progressBar;

        public ContentViewHolderLarge(View contentView) {
            super(contentView);
            progressBar = (ProgressBar) contentView.findViewById(R.id.progress);
            tv_share = (TextView) contentView.findViewById(R.id.tv_share);
            card = (CardView) contentView.findViewById(R.id.card);
            tv_title = (TextView) contentView.findViewById(R.id.tv_title);
            tv_info = (TextView) contentView.findViewById(R.id.tv_info);
            tv_time = (TextView) contentView.findViewById(R.id.tv_time);
            img = (ImageView) contentView.findViewById(R.id.img);
        }
    }
}
