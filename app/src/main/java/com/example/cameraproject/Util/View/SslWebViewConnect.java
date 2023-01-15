package com.example.cameraproject.Util.View;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//웹뷰 로드시 SSL 인증서 에러 방지
public class SslWebViewConnect extends WebViewClient {

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed(); // SSL 에러가 발생해도 계속 진행!
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;//응용프로그램이 직접 url를 처리함
    }
}