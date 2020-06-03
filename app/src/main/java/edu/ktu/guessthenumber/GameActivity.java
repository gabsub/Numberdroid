package edu.ktu.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private  int minNumber = 0;
    private  int maxNumber;

    private  int randomNumber;

    private int maxTurns;
    private int currentTurn = 1;

    private int result = 0;

    private TextView numberRangeText;
    private TextView resultText;
    private TextView turnsText; //ejimu skc

    private EditText numberField; //irasomas skaiciukas(spejimas)

    private static final String PREFS_FILE = "Prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        maxTurns = setMaxTurns();
        maxNumber = setMaxNumber();

        setContentView(R.layout.activity_game);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        numberRangeText = findViewById(R.id.number_range_txt); //(R.id.playerName);
        resultText = findViewById(R.id.result_txt);
        turnsText = findViewById(R.id.turns_txt);
        numberField = findViewById(R.id.number_field);
        updateTexts(0);
        Random random = new Random();
        randomNumber = random.nextInt(maxNumber - minNumber) + minNumber; //kad gauti random numeri tarp reziu
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent();
        switch (item.getItemId()){
            case R.id.item_about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.item_results:
                intent = new Intent(this, ResultsActivity.class);
                startActivity(intent);
                return true;
            case R.id.item_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateTexts(int guessNumber){
        numberRangeText.setText(String.format(getResources().getString(R.string.number_range_format), minNumber, maxNumber));//is strings failo paima reiksme
        if (result == -1){
            //perdidelis
            resultText.setText(String.format(getResources().getString(R.string.result_format), guessNumber, getResources().getString(R.string.result_high)));
            Toast.makeText(getApplicationContext(), String.format(getResources().getString(R.string.result_format), guessNumber, getResources().getString(R.string.result_high)), Toast.LENGTH_SHORT).show();

        }
        else if (result == 1){
            //permazas
            resultText.setText(String.format(getResources().getString(R.string.result_format), guessNumber , getResources().getString(R.string.result_low)));
            Toast.makeText(getApplicationContext(), String.format(getResources().getString(R.string.result_format), guessNumber , getResources().getString(R.string.result_low)), Toast.LENGTH_SHORT).show();
        }
        turnsText.setText(String.format(getResources().getString(R.string.turns_format), currentTurn, randomNumber));//currentTurn ir maxTurns isiraso i stringa
    }

    public  void  guessClick(View view)
    {
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        boolean sound = prefs.getBoolean("sound", true);
        if (sound == true){
            final MediaPlayer clickSound = MediaPlayer.create(this, R.raw.meow);
            clickSound.start();
        }

        currentTurn++; //atliktas vienas ejimas

        int guessNumber = Integer.parseInt(numberField.getText().toString()); //pasiverciam ivesta skaiciu is stringo i int
        if (randomNumber < guessNumber){
            result = -1;
        }
        else if(randomNumber > guessNumber){
            result = 1;
        }
        //jeigu atspejam, result islieka 0
        else
            result = 0;
        if (currentTurn >= maxTurns && result !=0){
            //Lose
            Intent intent = new Intent(this, GameOverActivity.class);
            intent.putExtra("guessedNumber", guessNumber);
            intent.putExtra("randomNumber", randomNumber);
            intent.putExtra("turnsCount", currentTurn);
            intent.putExtra("win", false);
            startActivity(intent);
            finish();
        }
        else if(result == 0){
            //Win
            Intent intent = new Intent(this, GameOverActivity.class);
            intent.putExtra("guessedNumber", guessNumber);
            intent.putExtra("randomNumber", randomNumber);
            intent.putExtra("turnsCount", currentTurn);
            intent.putExtra("win", true);
            startActivity(intent);
            finish();
        }
        updateTexts(guessNumber);
    }

    private int setMaxTurns(){
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);

        int diff = prefs.getInt("difficulty", 0);
        switch(diff)
        {
            case 0:
                return 15;
            case 1:
                return 10;
            case 2:
                return 7;
            case 3:
                return 2;
        }
        return 0;
    }

    private int setMaxNumber(){
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);

        int diff = prefs.getInt("difficulty", 0);
        switch(diff)
        {
            case 0:
                return 25;
            case 1:
                return 50;
            case 2:
                return 100;
            case 3:
                return 100;
        }
        return 0;
    }

}
