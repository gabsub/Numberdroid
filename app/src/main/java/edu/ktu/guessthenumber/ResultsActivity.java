package edu.ktu.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity implements WebTask.WebRequestFinished {

    private RecyclerView recyclerView; //nuoroda i layout esanti sarasiuka duomenu, kuriuos noresim ideti
    private RecyclerView.Adapter adapter; //objektas, susieja sarasa su duomenim, kuriuos pateiksiu adapteriui ir noresiu atvaizduot
    private RecyclerView.LayoutManager layoutManager; //nurodo kaip bus isdelioti duomenys sarase ekrane
    private ArrayList<PersonData> resultsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //del uzklausos
        //WebTask resultRequest = new WebTask();
        //resultRequest.setWebRequestFinished(this);
        //resultRequest.execute("http://gamegarden.lt/api/");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        resultsData = new ArrayList<>();
        prepareContent();

        recyclerView = findViewById(R.id.result_recycler_view);
        layoutManager = new LinearLayoutManager(this); //visus duomenis isdelios tiesiskai
        adapter = new CustomAdapter(resultsData, getResources().getStringArray(R.array.difficulty_items));

        recyclerView.setLayoutManager(layoutManager); //nustatom koki manageri naudot
        recyclerView.setAdapter(adapter); //nustatom koki adapteri naudoti

        adapter.notifyDataSetChanged(); //nurodo, kad pasikeite duomenu rinkinys ir reikia kazka daryt
        // geriau naudot adapter.notifyItemChanged(); kad nesvaistyt resursu
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

    private void prepareContent()
    {
        resultsData = new ArrayList<>();

        ResultsDatabaseHandler dbhandler = new ResultsDatabaseHandler(this);

        ArrayList<PersonData> data = dbhandler.getAllData();

        resultsData = data;
    }

    @Override
    public void webRequestResult(String s) {
        try{
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String name = jsonObj.getString("name");
                int number = jsonObj.getInt("number");
                int result = jsonObj.getInt("result");
                Log.w("WebTask", name + " " + number + " " + result);
                //cia reiktu tikrinti, ar nera duombazeje ir irasyti i ja, jei nera
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.w("WebTask", "nepaejo JSON");
        }
    }
}
