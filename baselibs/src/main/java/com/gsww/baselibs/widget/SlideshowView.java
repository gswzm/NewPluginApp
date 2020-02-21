package com.gsww.baselibs.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.gsww.baselibs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合自定义控件 轮播图
 */
public class SlideshowView extends RelativeLayout {

    private ViewPager viewPager;

    //存放小圆点的线性布局
    private LinearLayout llDot;
    private Integer dotColorNormal;
    private Integer dotColorHighLighted;

    private List<String> paths;
    private List<ImageView> imageViews;

    //记录上一次位置，改变指示器小圆点颜色
    private int lastPosition;

    //控制线程结束
    private boolean isLoop;

    //viewPager条目总数(假无线循环)
    private static final int MAX_SCROLL_VALUE = 50000;
    private Thread thread;

    public SlideshowView(Context context) {
        this(context, null);
    }

    public SlideshowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideshowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化控件
     *
     * @param context
     */
    private void initView(Context context) {
        View view = inflate(context, R.layout.base_layout_slides_show, this);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        llDot = (LinearLayout) view.findViewById(R.id.ll_dot);
        //设置默认小圆点颜色
        dotColorNormal = getResources().getColor(R.color.gray);
        dotColorHighLighted = getResources().getColor(R.color.common_blue);
    }

    /**
     * 动态传入图片url/地址集合
     *
     * @param paths
     */
    public void init(List<String> paths, Integer dotColorNormal, Integer dotColorHighLighted) {
        this.paths = paths;
        if (dotColorNormal != null) {
            this.dotColorNormal = dotColorNormal;
        }
        if (dotColorHighLighted != null) {
            this.dotColorHighLighted = dotColorHighLighted;
        }
        initData();
        initListener();
    }

    /**
     * 开始无限循环
     */
    private void startLoop() {
        //延时2秒到下一张
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isLoop) {
                    try {
                        //延时2秒到下一张
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            int currentItem = viewPager.getCurrentItem();
                            viewPager.setCurrentItem(++currentItem);
                        }
                    });
                }
            }
        });
        thread.start();
    }

    private void initData() {
        imageViews = new ArrayList<>();
        if (paths != null && paths.size() > 0) {
            int i = 0;
            for (String path : paths) {
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(getContext()).load(path).into(imageView);
                imageViews.add(imageView);

                //添加指示器
                CircleView dot = new CircleView(getContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
                //设置小圆点间距，第0个除外
                if (i != 0) {
                    layoutParams.leftMargin = 10;
                }
                llDot.addView(dot, layoutParams);
                i++;
            }
        }
        viewPager.setAdapter(new MyPagerAdapter());

        //viewPager默认位置在中间，向左向右都能无线循环
        int middlePosition = (MAX_SCROLL_VALUE / 2) - (MAX_SCROLL_VALUE / 2) % imageViews.size();
        viewPager.setCurrentItem(middlePosition);
        //设置默认小圆点颜色
        for (int i = 0; i < llDot.getChildCount(); i++) {
            CircleView view = (CircleView) llDot.getChildAt(i);
            if (i == middlePosition % imageViews.size()) {
                view.setCircleBg(dotColorHighLighted);
                continue;
            }
            view.setCircleBg(dotColorNormal);
        }
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return MAX_SCROLL_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position % imageViews.size());
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void initListener() {
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                int newPosition = position % imageViews.size();
//
//                //修改指示器小圆点颜色
//                ((CircleView) llDot.getChildAt(newPosition)).setCircleBg(dotColorHighLighted);
//                ((CircleView) llDot.getChildAt(lastPosition)).setCircleBg(dotColorNormal);
//
//                lastPosition = newPosition;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isLoop) {
            isLoop = true;
            startLoop();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //退出循环，结束线程
        isLoop = false;
        //在有缓存复用机制的列表中使用要中断线程，否则会创建多个线程，导致轮播频率不正常
        if (thread != null) {
            thread.interrupt();
        }
    }

}
