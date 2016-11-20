package game.finalproject.cse491.pairmatching;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mihodihasan on 11/15/16.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME="scoreboard.db";
    public static final String TABLE_NAME="LeaderBoard";
    public static final String Col1="ID";
    public static final String Col2="Name";
    public static final String Col3="Score";
    public static final String Col4="Taps";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+" ("+Col1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+Col2
                +" TEXT, "+Col3+" INTEGER, "+Col4+" INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME+";");
        onCreate(sqLiteDatabase);
    }

    public boolean insertScore(String name, String score, String taps){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Col2,name);
        values.put(Col3,score);
        values.put(Col4,taps);
        long result=database.insert(TABLE_NAME,null,values);
        if (result==-1){
            return false;
        }
        return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }
}
