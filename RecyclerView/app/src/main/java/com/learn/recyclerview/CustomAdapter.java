package com.learn.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ucoker on 16/3/10.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    public static class Bean implements Serializable{
//        public ImageView image;
        public String title;
        public String subTitle;
        public int height;
        public int width;

        public Bean() {
            super();
        }

        @Override
        public String toString() {
            return "title:" + title + "\n" +
                    "sub title:" + subTitle;
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }


    private ArrayList<Bean> mBeans;

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public CustomAdapter(ArrayList<Bean> beans) {
        mBeans = beans;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_row_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Bean bean = mBeans.get(position);
        holder.getTitle().setText(bean.title);
        holder.getSubTitle().setText(bean.subTitle);

        holder.getRelativeLayout().getLayoutParams().height = bean.height;
        holder.getRelativeLayout().getLayoutParams().width = bean.width;

        if (mOnItemClickLitener != null) {
            holder.getRelativeLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(v, pos);
                }
            });

            holder.getRelativeLayout().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mBeans.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView mImageView;
        private final TextView mTitle;
        private final TextView mSubTitle;
        private final RelativeLayout mLayout;

        public ViewHolder(View v) {
            super(v);
            mImageView = (ImageView)v.findViewById(R.id.imageView);
            mTitle = (TextView)v.findViewById(R.id.title);
            mSubTitle = (TextView)v.findViewById(R.id.subTitle);
            mLayout = (RelativeLayout)v.findViewById(R.id.relativelayout);
        }

        public ImageView getImageView() {
            return mImageView;
        }

        public TextView getTitle() {
            return mTitle;
        }

        public TextView getSubTitle() {
            return mSubTitle;
        }

        public RelativeLayout getRelativeLayout() {
            return mLayout;
        }
    }

}
