package edu.ktu.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_FILE = "Prefs";
    int m_number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        Button startGameBtn = findViewById(R.id.start_game_btn);
        Button resultsBtn = findViewById(R.id.results_btn);
        Button settingsBtn = findViewById(R.id.settings_btn);
        Button aboutBtn = findViewById(R.id.about_btn);
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
            case R.id.item_start:
                intent = new Intent(this, GameActivity.class);
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

    public void startClick(View view) {
        //keicia header reiksme pagal clickus
        /*TextView header = findViewById(R.id.header);
        m_number++;
        header.setText(Integer.toString(m_number));*/

        //intent - pranesimas, kurio klauso aktiviciai
        //atidarys nauja langa

        SharedPreferences prefs = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        boolean sound = prefs.getBoolean("sound", true);
        if (sound == true){
            final MediaPlayer clickSound = MediaPlayer.create(this, R.raw.meow);
            clickSound.start();
        }

        if (view == findViewById(R.id.start_game_btn)) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        } else if (view == findViewById(R.id.results_btn)) {
            Intent intent = new Intent(this, ResultsActivity.class);
            startActivity(intent);
        } else if (view == findViewById(R.id.settings_btn)) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (view == findViewById(R.id.about_btn)) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
    }
}
