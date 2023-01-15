package com.example.cameraproject.Util.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.cameraproject.R;



public class DaumWebViewActivity extends AppCompatActivity {
    private WebView webView;
    private WebView mWebViewPop;
    private TextView txt_address;
    private Handler handler;
    private FrameLayout mContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daum_web_view);

        txt_address = findViewById(R.id.txt_address);
        mContainer = findViewById(R.id.daum_webview_frame);
        webView = findViewById(R.id.daum_webView);


        // WebView 초기화
        init_webView();

        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();
    }





    public void init_webView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new AndroidBridge(), "CameraProject");
        webView.addJavascriptInterface(new MyJavascriptInterface(), "CameraProject");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                Toast.makeText(DaumWebViewActivity.this,"로딩 끝", Toast.LENGTH_SHORT).show();
            }
        });


        webView.loadUrl("http://ec2-52-79-74-180.ap-northeast-2.compute.amazonaws.com/iframe.html");
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    txt_address.setText(String.format("(%s) %s %s", arg1, arg2, arg3));
                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    init_webView();

                }
            });
        }
    }

    public class MyJavascriptInterface{
        @JavascriptInterface
        public void getHtml(String html){
            System.out.println("shin"+html);
        }
    }

}