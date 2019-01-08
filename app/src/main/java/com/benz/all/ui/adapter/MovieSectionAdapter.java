package com.benz.all.ui.adapter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.benz.all.R;
import com.benz.all.entity.movie.tiantan.Subjects;
import com.benz.all.utils.Utils;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class MovieSectionAdapter extends BaseSectionQuickAdapter<Subjects, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public MovieSectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final Subjects item) {
        helper.setText(R.id.tvTitle, item.header);
        helper.setVisible(R.id.tvLine, !"高分电影".equals(item.getParentName()));
    }

    @Override
    protected void convert(BaseViewHolder helper, Subjects item) {
        ImageView ivThumb = helper.getView(R.id.ivThumb);
        helper.setText(R.id.tvName, Utils.noNull(item.getName()));
        helper.setText(R.id.tvScore, "" + item.getScore());
        if (item.getImg() != null) {
            Picasso.with(mContext)
                    .load(item.getImg())
                    .placeholder(R.mipmap.ic_movie_placeholder)
                    .error(R.mipmap.ic_movie_placeholder)
                    .config(Bitmap.Config.RGB_565)
                    .tag(mContext)
                    .into(ivThumb);
        } else {
            ivThumb.setImageResource(R.mipmap.ic_news_placeholder);
        }
    }
}
