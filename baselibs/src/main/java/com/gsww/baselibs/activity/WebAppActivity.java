package com.gsww.baselibs.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gsww.baselibs.R;
import com.gsww.baselibs.R2;

import butterknife.BindView;


/**
 * Created by wujd on 2015/2/16.
 */
public class WebAppActivity extends BaseActivity {

    @BindView(R2.id.web_view)
    WebView webView;
    private String title = "";
    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private String url;
    private String content;
    private ProgressBar progressbar;

    @Override
    protected void initTitle() {
        setTitle(title);
        progressbar = new ProgressBar(activity, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setMax(100);

        initWebView();
        if(url !=null && !url.equals("")){
            webView.loadUrl(url);
        }else if(content!=null && !content.equals("")){
            webView.loadDataWithBaseURL("about:blank",content, "text/html", "utf-8",null);
        }else{
            showToast("未接收到要访问的url地址！");
            finish();
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        title=extras.getString("title");
        url = extras.getString("url");
        content = extras.getString("content");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_web;
    }

    /**
     * 初始化浏览器参数
     */
    @SuppressLint("JavascriptInterface")
    private void initWebView(){
        String dir = this.getApplicationContext().getDir("database", MODE_PRIVATE).getPath();
        webView = (WebView) findViewById(R.id.web_view);
        webView.setWebViewClient(new webViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        //html页面大小自适应
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setSupportZoom(false);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //启用地理定位
        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath(dir);
        webSettings.setDatabasePath(dir);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);  //图片适应屏幕
        webView.requestFocus();

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressbar.setVisibility(View.GONE);
                } else {
                    if (progressbar.getVisibility() == View.GONE)
                        progressbar.setVisibility(View.VISIBLE);
                    progressbar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                if (mUploadMessage != null) return;
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult( Intent.createChooser( i, "File Chooser" ), WebAppActivity.FILECHOOSER_RESULTCODE );
            }
            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser( uploadMsg, "" );
            }
            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooser(uploadMsg, acceptType);
            }


            //		@Override
//		public boolean onJsAlert(WebView view, String url, String message,
//				JsResult result) {
//			return true;
//		}
            @Override
            public void onExceededDatabaseQuota(String url,
                                                String databaseIdentifier, long currentQuota,
                                                long estimatedSize, long totalUsedQuota,
                                                WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(estimatedSize*2);
            }
            @Override
            public void onReachedMaxAppCacheSize(long spaceNeeded,
                                                 long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
                super.onReachedMaxAppCacheSize(spaceNeeded, totalUsedQuota, quotaUpdater);
            }
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
            @Override
            public boolean onJsPrompt(WebView view, String url, String message,
                                      String defaultValue, final JsPromptResult result) {
                new AlertDialog.Builder(activity).setTitle("来自网页的消息").setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }

                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //处理结果为取消状态 同时唤醒WebCore线程
                        result.cancel();
                    }
                }).create().show();
                return true;
            }
            @Override
            public boolean onJsConfirm(WebView view, String url, String message,
                                       final JsResult result) {
                new AlertDialog.Builder(activity).setTitle("来自网页的消息").setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }

                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //处理结果为取消状态 同时唤醒WebCore线程
                        result.cancel();
                    }
                }).create().show();
                return true;
            }
        });
    }

    class webViewClient extends WebViewClient {
        //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            //如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }
    }

    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
