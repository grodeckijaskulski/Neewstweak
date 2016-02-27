package com.example.macie_000.neewstweak;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by macie_000 on 2015-06-14.
 */
public class NewsList extends Activity {
    private ArrayList<News> l_News;
    private Reader oReader = new Reader();
    private ListView oListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newslist);
        oListView = (ListView)findViewById(R.id.listView);

        Intent intent1 = getIntent();
        if(intent1.getExtras() != null) {


            String sChannel = intent1.getExtras().getString("channel");
            GetData(sChannel);
        }
        else
        {
            Database db = new Database(this);
            l_News = db.SelectAllFavorites();
            ArrayList<String> resultArray = GetSpecifyItem(0);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    resultArray);
            oListView.setAdapter(arrayAdapter);
        }
        oListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent oIntent = new Intent(NewsList.this, DescNews.class);
                oIntent.putExtras(DescNewsBundler.newDescNewsBundler(l_News, position));
                startActivity(oIntent);


            }
        });

        oListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Database oDatabase = new Database(getApplicationContext());
                boolean result = oDatabase.ExistingCheck(l_News.get(i));
                if(oDatabase.ExistingCheck(l_News.get(i)) == false)
                {
                    oDatabase.Insert(l_News.get(i));
                    Toast.makeText(getApplicationContext(), "Dodano do zajebiaszcze",Toast.LENGTH_SHORT).show();
                }
                else{
                    oDatabase.Delete(l_News.get(i));
                    Toast.makeText(getApplicationContext(), "Usunięto z zajebiaszcze",Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }


                return true;
            }
        });
    }





    private ArrayList<String> GetSpecifyItem(int iItemIndex)
    {
        ArrayList<String> a_Result = new ArrayList<String>();

        for(int i=0;i<l_News.size();i++){
            a_Result.add(l_News.get(i).getTitle());
        }

        return a_Result;
    }

    public ArrayList<News> GetFirstItem() throws MalformedURLException {
        //News sResult = new News();
        oReader.setURL("http://wiadomosci.wp.pl/kat,1329,ver,rss,rss.xml");
        ArrayList<String> a_Result = new ArrayList<String>();
        l_News = oReader.Read();
        return  l_News;
        //for(int i=0;i<l_News.size();i++){
         //   a_Result.add(l_News.get(i).getTitle());
       // }
        //sResult = l_News.get(0);
        //sResult = a_Result.get(0);
        //return  sResult;
    }
/*
    public String GetFirstItemLink() throws MalformedURLException {
        String sResult = "";
        ArrayList<String> a_Result = new ArrayList<String>();
        for(int i=0;i<l_News.size();i++){
            a_Result.add(l_News.get(i).getLink());
        }

        sResult = a_Result.get(0);
        return  sResult;
    }
*/

    private void GetData(String sChannelName) {
        try {
            oReader.setURL(sChannelName);
            l_News = oReader.Read();
            ArrayList<String> resultArray = GetSpecifyItem(0);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    resultArray);
            oListView.setAdapter(arrayAdapter);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
