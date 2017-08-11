package com.example.shivam.datcompressorfinal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shivam on 3/4/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME="MINOR_PROJECT";
    public static final int DATABASE_VERSION=1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE  IF NOT EXISTS FILEUPDATES (ID INTEGER AUTO_INCREMENT,FILENAME VARCHAR(50) NOT NULL ,NOTIFICATION_MESAGE VARCHAR(200) NOT NULL , UPDATETIME TIMESTAMP, UPDATEDATE DATE , PRIMARY KEY(ID) )";
        db.execSQL(sql);
        sql="CREATE TABLE  IF NOT EXISTS GESTURELIST (ID INTEGER AUTO_INCREMENT, GESTURENAME VARCHAR(50) NOT NULL , PRIMARY KEY(GESTURENAME,ID) )";
        db.execSQL(sql);
        //db.close();
    }

    public void insertgesturename(String gesturename)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="INSERT INTO GESTURELIST (GESTURENAME) VALUES( '"+gesturename.trim()+"'); ";
        db.execSQL(sql);
        db.close();
    }

    public boolean checkgesturename(String gesturename)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT * FROM GESTURELIST where GESTURENAME='"+gesturename.trim()+"';";
        Cursor cursor=db.rawQuery(sql,null);

        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {       String gest=cursor.getString(cursor.getColumnIndex("GESTURENAME"));
            //Log.e("FOUND GESTURE" ,"gesture foing 2"+ String.valueOf(cursor.getCount())+gest );
            //db.close();
            return  true;}
        //db.close();
        return false;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
