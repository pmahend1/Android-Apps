package com.uncc.prateek.gamesdb;
/*
    a. Assignment #. Homework 05
	b. File Name. ViewTrailerActivity.java
	c. Full name of all students in your group. Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
*/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class ViewTrailerActivity extends AppCompatActivity {
    private static String URLBase = "http://thegamesdb.net/api/GetGamesList.php?name=";//http://wiki.thegamesdb.net/index.php/GetGamesList";
    private static String URL = "";
    private String searchKey = "";
    private String logText = "ViewTrailer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_view_trailer);

        String youTubeLink = (String) getIntent().getSerializableExtra("YOUTUBE_URL");
        String gameName = (String) getIntent().getSerializableExtra("GAME_NAME");

        if (youTubeLink != null) {
            Log.d(logText, "YouTube link :" + youTubeLink);
            TextView tvName = (TextView) findViewById(R.id.textView_trailerGameName);
            tvName.setText(gameName + "trailer");

            WebView wvTrailer = (WebView) findViewById(R.id.webView_youTubeLink);
            youTubeLink = youTubeLink.replace("watch?v=", "embed/");
            Log.d(logText, "YouTube link :" + youTubeLink);

            String youTubeLinkEnc = "<html><body><iframe width='320' height='315' src=" + youTubeLink + " frameborder='0' allowfullscreen></iframe></body></html>";
            Log.d(logText, youTubeLinkEnc);

            wvTrailer.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
            WebSettings webSettings = wvTrailer.getSettings();
            webSettings.setJavaScriptEnabled(true);
            wvTrailer.loadData(youTubeLinkEnc, "text/html", "utf-8");

        } else {
            Log.d(logText, "youTube link sent was NULL");
        }
    }
}
