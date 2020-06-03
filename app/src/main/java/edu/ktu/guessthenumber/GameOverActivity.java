package edu.ktu.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GameOverActivity extends AppCompatActivity {

    private TextView winText;
    private ImageView imagePic;
    private static final String PREFS_FILE = "Prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        winText = findViewById(R.id.win_txt);
        imagePic = findViewById(R.id.imageView6);
        Intent intent = getIntent();//is kito activity paima
        boolean win = intent.getBooleanExtra("win", false);//raktas win toks pat turi but pagal nutylejima reiksme false
        int guessedNumber = intent.getIntExtra("randomNumber", 0);
        int turnsCount = intent.getIntExtra("turnsCount", 0);

        if (win){
            winText.setText(getResources().getString(R.string.text_win));
            imagePic.setImageResource(R.drawable.horse3);
            addDataToResults(guessedNumber, turnsCount);
        }
        else{
            winText.setText(getResources().getString(R.string.text_lose));
            imagePic.setImageResource(R.drawable.horse2);
        }
    }

    private void addDataToResults(int guessedNumber, int turnsCount)
    {
        ResultsDatabaseHandler dbhandler = new ResultsDatabaseHandler(this);
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);

        String name = prefs.getString("playerName", "Name");
        int age = prefs.getInt("playerAge", 1);
        int difficulty = prefs.getInt("difficulty", 0);

        dbhandler.addData(new PersonData(0, name, difficulty, age, guessedNumber, turnsCount));

    }
}
