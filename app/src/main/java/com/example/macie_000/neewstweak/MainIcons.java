package com.example.macie_000.neewstweak;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import java.net.MalformedURLException;

/**
 * Created by Marian on 2016-01-31.
 */
public class MainIcons extends Activity {
    private TextView txtLastNews;
    NewsList oNewsList = new NewsList();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_icons);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        txtLastNews = (TextView)findViewById(R.id.txtLastNews);

        try {
            txtLastNews.setText(oNewsList.GetFirstItem().get(0).getTitle());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public void btnNews_onClick(View oView){
        Intent oIntent = new Intent(this,MainActivity.class);
        startActivity(oIntent);

    }

    public void txtLastNews_onClick(View oView) throws MalformedURLException {
        Intent oIntent = new Intent(MainIcons.this, DescNews.class);
        oIntent.putExtras(DescNewsBundler.newDescNewsBundler(oNewsList.GetFirstItem(),0));
        startActivity(oIntent);

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void  btnOff_onClick(View oView)
    {
        this.finishAffinity();
    }

    public void btnGreat_onClick(View oView)
    {
        Intent oIntent = new Intent(this,NewsList.class);
        startActivity(oIntent);
    }
}
