package game.finalproject.cse491.pairmatching;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper=new DBHelper(this);
    }

    public void play(View view) {
        startActivity(new Intent(MainActivity.this,PlayGame.class));
    }


    public void showLeaderBoard(View view) {
        Cursor cursor=dbHelper.getAllData();
//        String score;
        if(cursor.getCount()==0){
            showInDialog("No Score!","Yet None Saved Any Score to This LeaderBoard." +
                    " Try Playing in Minimum Taps And Save in This Leaderboard");
        }else {
            StringBuffer buffer=new StringBuffer();
            while (cursor.moveToNext()){
                buffer.append("Name: "+cursor.getString(1)+"\n");
                buffer.append("Taps: "+cursor.getString(3)+"\n\n");
            }
            showInDialog("Score Board",buffer.toString());
        }
    }


    public void showInDialog(String title,String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true)
                .setTitle(title)
                .setMessage(msg)
                .show();
    }
}
