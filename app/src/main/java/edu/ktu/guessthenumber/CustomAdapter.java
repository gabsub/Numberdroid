package edu.ktu.guessthenumber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{ //paveldejimas, nurodom kokio tipo adapteris bus

    private List<PersonData> data; //duomenys kaip stringu sarasas
    private String[] difficulties;

    public static class CustomViewHolder extends RecyclerView.ViewHolder { //klase, kuri laiko visus vieno iraso atvaizduojamuosius laukus
        public TextView nameView;
        public TextView difficultyView;
        public TextView turnsCountView;
        public TextView idView;

        public CustomViewHolder(View view) //konstruktorius
        {
            super(view);
            this.nameView = view.findViewById(R.id.results_name);
            this.difficultyView = view.findViewById(R.id.results_difficulty);
            this.turnsCountView = view.findViewById(R.id.results_turns_count);
            this.idView = view.findViewById(R.id.results_ID);
        }
    }

    CustomAdapter(List<PersonData> data, String[] difficulties)
    {
        this.data = data;
        this.difficulties = difficulties;
    } //konstruktorius

    //sitie trys butinai overridinami, kitaip programa neveiks
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) //sukuria viena viewholder objekta, i kuri paskui bus idedami duomenys
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(itemView);
        return viewHolder;
    }

    public void onBindViewHolder(CustomViewHolder viewHolder, int position) //kad uzpildytu view objekta duomenimis
    {
        PersonData person = data.get(position);
        viewHolder.idView.setText(Integer.toString(person.getID()));
        viewHolder.nameView.setText(person.getName());
        int diff = person.getDifficulty();
        String difficulty = difficulties[diff];
        /*pataisyt
        switch(diff)
        {
            case 0:
                difficulty = difficulties[0];
                break;
            case 1:
                difficulty = difficulties[1];
                break;
            case 2:
                difficulty = difficulties[2];
                break;
            case 3:
                difficulty = difficulties[3];
                break;
        }*/
        viewHolder.difficultyView.setText(difficulty);
        viewHolder.turnsCountView.setText(Integer.toString(person.getTurnsCount()));
    }

    public int getItemCount()
    {
        return data.size();
    } //suzino kiek is viso duomenu yra musu sarase
}
