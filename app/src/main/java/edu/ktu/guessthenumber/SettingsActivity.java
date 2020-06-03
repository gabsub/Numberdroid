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
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_FILE = "Prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        SharedPreferences prefs = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);

        String name = prefs.getString("playerName", "Name");
        int age = prefs.getInt("playerAge", 1);
        int difficulty = prefs.getInt("difficulty", 0);
        boolean sound = prefs.getBoolean("sound", true);

        EditText playerNameField = findViewById(R.id.playerName);
        EditText playerAgeField = findViewById(R.id.playerAge);
        Spinner difficultySpinner = findViewById(R.id.difficultySpinner);
        Switch soundSwitch = findViewById(R.id.soundSwitch);

        playerNameField.setText(name);
        playerAgeField.setText(Integer.toString(age));
        difficultySpinner.setSelection(difficulty);
        soundSwitch.setChecked(sound);

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
            case R.id.item_start:
                intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveClick(View view){
        SharedPreferences.Editor prefsEditor = getSharedPreferences(PREFS_FILE, MODE_PRIVATE).edit();
        EditText playerNameField = findViewById(R.id.playerName);
        EditText playerAgeField = findViewById(R.id.playerAge);
        Spinner difficultySpinner = findViewById(R.id.difficultySpinner);
        Switch soundSwitch = findViewById(R.id.soundSwitch);

        String name = playerNameField.getText().toString();
        int age = Integer.parseInt(playerAgeField.getText().toString());
        int difficulty = difficultySpinner.getSelectedItemPosition();
        boolean sound = soundSwitch.isChecked();

        if (sound == true){
            final MediaPlayer clickSound = MediaPlayer.create(this, R.raw.meow);
            clickSound.start();
        }

        prefsEditor.putString("playerName", name);
        prefsEditor.putInt("playerAge", age);
        prefsEditor.putInt("difficulty", difficulty);
        prefsEditor.putBoolean("sound", sound);

        prefsEditor.apply();

        finish();
    }
}
