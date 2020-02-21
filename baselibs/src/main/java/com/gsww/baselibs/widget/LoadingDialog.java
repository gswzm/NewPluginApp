package com.gsww.baselibs.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.gsww.baselibs.R;


public class LoadingDialog {

    private final Dialog dialog;

    public LoadingDialog(Context context) {
        dialog = new Dialog(context, R.style.LoadingDialog);

        View view = LayoutInflater.from(context).inflate(R.layout.base_layout_loading_view, null);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lp);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();

        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = 300;
        layoutParams.height = 300;
        layoutParams.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(layoutParams);
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
