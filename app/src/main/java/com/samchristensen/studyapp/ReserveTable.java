package com.samchristensen.studyapp;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;


public class ReserveTable extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_table);

        String url = "https://library.ems.wis.edu/VirtualEMS_UW/";
        WebView view = (WebView) findViewById(R.id.reservetable_webview);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);

    }

}
