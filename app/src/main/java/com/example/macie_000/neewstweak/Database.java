package com.example.macie_000.neewstweak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by macie_000 on 2016-02-06.
 */
public class Database extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 1;

    private static final  String TABLE_FAVORITES = "favorites";

    //favorites table columns
    private  static final String KEY_FAVORITES_ID = "id";
    private static final String KEY_FAVORITES_TITLE = "title";
    private static final String KEY_FAVORITES_DESCIRIPTION = "description";
    private static final String KEY_FAVORITES_LINK = "link";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sCreateTable = "CREATE TABLE " + TABLE_FAVORITES +
                "(" +
                KEY_FAVORITES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_FAVORITES_TITLE + " TEXT," +
                KEY_FAVORITES_DESCIRIPTION + " TEXT," +
                KEY_FAVORITES_LINK + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(sCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            // Simplest implementation is to drop all old tables and recreate them
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
            onCreate(sqLiteDatabase);
        }
    }

    public void Insert(News oNews)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FAVORITES_TITLE,oNews.getTitle());
        values.put(KEY_FAVORITES_DESCIRIPTION,oNews.getDescription());
        values.put(KEY_FAVORITES_LINK,oNews.getLink());

        database.insert(TABLE_FAVORITES,null,values);
    }

    public ArrayList<News> SelectAllFavorites()
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ArrayList<News> favorites = new ArrayList<>();

        Cursor c = database.rawQuery("SELECT * FROM favorites ORDER BY id DESC", null);
        if(c.moveToFirst()) {
            do {
                News oNews = new News();
                oNews.setId(Integer.parseInt(c.getString(0)));
                oNews.setTitle(c.getString(1));
                oNews.setDescription(c.getString(2));
                oNews.setLink(c.getString(3));
                favorites.add(oNews);
            } while (c.moveToNext());
        }
        return favorites;
    }

    public void Delete(News oNews){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_FAVORITES, KEY_FAVORITES_ID + " =?",
                new String[] {String.valueOf(oNews.getId())});
    }


    public boolean ExistingCheck(News oNews)
    {
        boolean result = false;

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor c = database.query(TABLE_FAVORITES, new String[] {KEY_FAVORITES_ID,
        KEY_FAVORITES_TITLE,KEY_FAVORITES_DESCIRIPTION,KEY_FAVORITES_LINK},KEY_FAVORITES_TITLE + "=?",
                new String[] {String.valueOf(oNews.getTitle())},null,null,null,null);
        if(c.getCount() != 0) {
            result = true;
        }

        return  result;
    }
}
