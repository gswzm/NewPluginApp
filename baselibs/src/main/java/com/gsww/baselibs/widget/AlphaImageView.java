package com.gsww.baselibs.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class AlphaImageView extends AppCompatImageView {
    public AlphaImageView(Context context) {
        super(context);
    }

    public AlphaImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (pressed) {
            setAlpha(0.7f);

            setScaleX(0.95f);
            setScaleY(0.95f);
        } else {
            setAlpha(1.0f);

            setScaleX(1.0f);
            setScaleY(1.0f);
        }
    }
}
