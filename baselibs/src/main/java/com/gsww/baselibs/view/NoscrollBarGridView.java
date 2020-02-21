package com.gsww.baselibs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2015/5/14.
 */
public class NoscrollBarGridView extends GridView {
        public NoscrollBarGridView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public NoscrollBarGridView(Context context) {
            super(context);
        }

        public NoscrollBarGridView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            int expandSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        }
    }
