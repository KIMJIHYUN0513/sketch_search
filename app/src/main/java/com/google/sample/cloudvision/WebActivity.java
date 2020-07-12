package com.google.sample.cloudvision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    //연결할 웹 URL setting
    private String URL_1 = "https://search.shopping.naver.com/search/all.nhn?query=";
    private String URL_2 = "&cat_id=&frm=NVSHATC";

    private String resultString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web);

        resultString = ((TranslationActivity) TranslationActivity.mContext).koreaString;

        String ResultURL = URL_1 + resultString + URL_2;

        //웹 연결
        WebView webView = (WebView) findViewById((R.id.webView));
        webView.getSettings().getJavaScriptEnabled();
        webView.loadUrl(ResultURL);


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Main_SketchActivity.class);
        finish();
        startActivity(intent);
    }

}
