package com.gsww.baselibs.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gsww.baselibs.R;


/**
 * Created by zhangjr on 2017/5/25.
 * Edit by yaochangliang 2019-02-27
 */

public class ImageUtil {
    //glide加载网络图片
    public static void loadImage(Context context, String url, ImageView imageView) {
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.jpzb_empty_img);
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void loadImage(Context context, String url, ImageView imageView, int placeHolderId) {
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(placeHolderId);
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }
}