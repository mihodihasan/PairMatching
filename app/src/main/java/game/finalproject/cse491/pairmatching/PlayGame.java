package game.finalproject.cse491.pairmatching;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class PlayGame extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper;
    ImageView prevImgView = null, currentIv;
    boolean oneClickedEarlier = false;
    boolean match = false;

    int clickCount;

    int imgId[];

    Button saveLeaderBoard;

    ImageView row1col1, row1col2, row1col3, row1col4, row2col1, row2col2, row2col3, row2col4, row3col1,
            row3col2, row3col3, row3col4, row4col1, row4col2, row4col3, row4col4;
    //
    ImageView imageViewArray[];
    private int score;

    TextView tv;
    EditText name;
    LinearLayout hiddenLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        hiddenLinearLayout = (LinearLayout) findViewById(R.id.hiddenContainer);
        name = (EditText) findViewById(R.id.name);
        saveLeaderBoard = (Button) findViewById(R.id.saveLeaderBoard);

        dbHelper = new DBHelper(this);

        tv = (TextView) findViewById(R.id.successMsg);

        clickCount = 0;
        score = 0;
        //Initialize Image Views
        row1col1 = (ImageView) findViewById(R.id.row1col1);
        row1col2 = (ImageView) findViewById(R.id.row1col2);
        row1col3 = (ImageView) findViewById(R.id.row1col3);
        row1col4 = (ImageView) findViewById(R.id.row1col4);
        row2col1 = (ImageView) findViewById(R.id.row2col1);
        row2col2 = (ImageView) findViewById(R.id.row2col2);
        row2col3 = (ImageView) findViewById(R.id.row2col3);
        row2col4 = (ImageView) findViewById(R.id.row2col4);
        row3col1 = (ImageView) findViewById(R.id.row3col1);
        row3col2 = (ImageView) findViewById(R.id.row3col2);
        row3col3 = (ImageView) findViewById(R.id.row3col3);
        row3col4 = (ImageView) findViewById(R.id.row3col4);
        row4col1 = (ImageView) findViewById(R.id.row4col1);
        row4col2 = (ImageView) findViewById(R.id.row4col2);
        row4col3 = (ImageView) findViewById(R.id.row4col3);
        row4col4 = (ImageView) findViewById(R.id.row4col4);

        //Array Set Of Images
//        imgId = new int[]{R.drawable.icon1,R.drawable.icon2,R.drawable.icon3,R.drawable.icon4,
//                R.drawable.icon5,R.drawable.icon6,R.drawable.icon7,R.drawable.icon8};
        imgId = new int[]{R.drawable.one, R.drawable.three, R.drawable.two, R.drawable.five,
                R.drawable.four, R.drawable.seven, R.drawable.six, R.drawable.zero};
        //Array Set of ImageViews
        imageViewArray = new ImageView[]{row1col1, row1col2, row1col3, row1col4, row2col1,
                row2col2, row2col3, row2col4, row3col1, row3col2, row3col3, row3col4,
                row4col1, row4col2, row4col3, row4col4};

        //Handle Click Listeners
        row1col1.setOnClickListener(this);
        row1col2.setOnClickListener(this);
        row1col3.setOnClickListener(this);
        row1col4.setOnClickListener(this);

        row2col1.setOnClickListener(this);
        row2col2.setOnClickListener(this);
        row2col3.setOnClickListener(this);
        row2col4.setOnClickListener(this);

        row3col1.setOnClickListener(this);
        row3col2.setOnClickListener(this);
        row3col3.setOnClickListener(this);
        row3col4.setOnClickListener(this);

        row4col1.setOnClickListener(this);
        row4col2.setOnClickListener(this);
        row4col3.setOnClickListener(this);
        row4col4.setOnClickListener(this);

        int rand[] = unique8Random();

        row1col1.setTag(rand[0]);
        row1col2.setTag(rand[1]);
        row1col3.setTag(rand[2]);
        row1col4.setTag(rand[3]);

        row2col1.setTag(rand[4]);
        row2col2.setTag(rand[6]);
        row2col3.setTag(rand[7]);
        row2col4.setTag(rand[8]);

        row3col1.setTag(rand[9]);
        row3col2.setTag(rand[10]);
        row3col3.setTag(rand[11]);
        row3col4.setTag(rand[12]);

        row4col1.setTag(rand[13]);
        row4col2.setTag(rand[14]);
        row4col3.setTag(rand[15]);
        row4col4.setTag(rand[5]);

    }


//    public int getBoxIndex(ImageView imageView) {
//        for (int i = 0; i < box.length; i++) {
//            if (box[i].imageView.equals(imageView)) {
//                return i;
//            }
//        }
//        return -1;
//    }

    public int[] unique8Random() {

        int[] numbers = new int[16];
        Random random = new Random();
        numbers[0] = random.nextInt(8);
        for (int i = 1; i < 16; i++) {
            boolean tag = false;
            int r = random.nextInt(8);

            int occurance = 0;
            for (int j = 0; j < i; j++) {
                if (numbers[j] == r) {
                    if (occurance == 0) {
                        occurance++;
                    } else {
                        tag = true;
                        break;
                    }
                }
            }
            if (tag == false) {
                numbers[i] = r;
            } else {
                i--;
            }
        }

        return numbers;
    }

    @Override
    public void onClick(View view) {
        clickCount++;
        if (match) {
            currentIv.setVisibility(View.INVISIBLE);
            prevImgView.setVisibility(View.INVISIBLE);
            match = false;
            oneClickedEarlier = false;
        }
        ImageView iv = (ImageView) view;

        iv.setImageDrawable(getDrawable(imgId[Integer.parseInt(iv.getTag() + "")]));
        if (oneClickedEarlier) {
            if (prevImgView.getTag() == iv.getTag()) {
                match = true;
                score++;
                Toast.makeText(PlayGame.this, "Your Score Is: " + score, Toast.LENGTH_SHORT).show();
                if (score == 8) {
                    hiddenLinearLayout.setVisibility(View.VISIBLE);
                    tv.setText("You Have Done It! Congrats!\n" +
                            "You Take " + clickCount + " Taps!\nTotal Score " + score+
                            "Enter Your Name And Tap OK if You wanna save this score"
                    );
                }
                currentIv = iv;
                currentIv.setClickable(false);
                prevImgView.setClickable(false);
            } else {
                iv.setImageDrawable(getDrawable(imgId[Integer.parseInt(iv.getTag() + "")]));
                prevImgView.setImageDrawable(getDrawable(R.drawable.icon));
                prevImgView = iv;
                oneClickedEarlier = true;
            }
        } else {
            oneClickedEarlier = true;
            prevImgView = iv;
        }
//        ImageView iv = (ImageView) view;
//        iv.setImageDrawable(getDrawable(imgId[Integer.parseInt(iv.getTag() + "")]));
//
//
//        if (match) {//close and deactivate prev 2 iv
//            prevImgView.setImageDrawable(getDrawable(R.drawable.icon));
//            prevImgView.setClickable(false);
//            currentIv.setImageDrawable(getDrawable(R.drawable.icon));
//            currentIv.setClickable(false);
//            match = false;
//        } else {
//            if (oneClickedEarlier == true) {
//                if (iv.getTag() == prevImgView.getTag()) {
//                    Toast.makeText(Home.this, "Matched", Toast.LENGTH_SHORT).show();
//                    currentIv = iv;
//                    match = true;
//                } else {
//                    match = false;
//                    currentIv = null;
//                    prevImgView = null;
//                }
//                oneClickedEarlier = false;
//            } else {
//                prevImgView = iv;
//                oneClickedEarlier = true;
//                iv.setImageDrawable(getDrawable(imgId[Integer.parseInt(iv.getTag() + "")]));
//            }
//        }
    }

    public void saveToLeaderBoard(View view) {
        if(name.getText().toString().equals("")){
            dbHelper.insertScore("Anonnymous","8",clickCount+"");
            Toast.makeText(this,"Score Saved Annonymously!",Toast.LENGTH_SHORT).show();
        }else {
        dbHelper.insertScore(name.getText().toString(), "8", clickCount + "");
        Toast.makeText(this, "Score Saved!", Toast.LENGTH_SHORT).show();
        }
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
