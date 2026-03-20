package com.dogi.lab8;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyBrowser extends AppCompatActivity {
    WebView browser;
    LinearLayout layout;
    EditText textUrl;
    Button btnUrl;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_browser);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        browser = (WebView) findViewById(R.id.webView);
        browser.setWebViewClient(new WebViewClient());
        layout = (LinearLayout) findViewById(R.id.url_bar);
        textUrl = (EditText) findViewById(R.id.text_url);
        btnUrl = (Button) findViewById(R.id.button_load);

        String url = getIntent().getStringExtra("url");
        Bundle extras = getIntent().getExtras();
        if (url == null || url.isEmpty()){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            url = preferences.getString("url", "http://");
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        browser.loadUrl(url);
        textUrl.setText(url);


        View.OnClickListener btnClick= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = textUrl.getText().toString();
                Bundle extras = getIntent().getExtras();
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                browser.loadUrl(url);

            }
        };

        btnUrl.setOnClickListener(btnClick);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean allowImage = preferences.getBoolean("image",true);
        boolean allowJavaScript = preferences.getBoolean("javascript", true);
        boolean allowPopup = preferences.getBoolean("popup", false);
        WebSettings settings = browser.getSettings();
        settings.setBlockNetworkImage(allowImage);
        settings.setJavaScriptEnabled(allowJavaScript);
        settings.setJavaScriptCanOpenWindowsAutomatically(allowPopup);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
            if (id == R.id.urlbar) {
                if (layout.getVisibility() == View.VISIBLE) {
                    layout.setVisibility(View.GONE);
                } else layout.setVisibility(View.VISIBLE);
            }
            if (id == R.id.refresh) {
                browser.reload();
            }
            if (id == R.id.back) {
                if (browser.canGoBack())
                    browser.goBack();
            }
            if (id == R.id.forward) {
                if (browser.canGoForward()) {
                    browser.goForward();
                }
            }
            if (id == R.id.settings) {
                Intent intent = new Intent();
                intent.setClass(this, MyPreferences.class);
                startActivity(intent);
            }
            if (id == R.id.exit){
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_browser,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (browser.canGoBack()) {
            browser.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
