package com.example.herdhoncho;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnimalsAdapter extends RecyclerView.Adapter<AnimalsAdapter.ViewHolder> {

    private final ArrayList<Animal> animalList;
    private final Context mContext;

    public AnimalsAdapter(ArrayList<Animal> animalsList, Context mContext){

        this.animalList = animalsList;
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tagNumber, breed, weight, age, relation, editAnimal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tagNumber = itemView.findViewById(R.id.animal_tagNumber);
            breed = itemView.findViewById(R.id.animal_breed);
            weight = itemView.findViewById(R.id.animal_weight);
            age = itemView.findViewById(R.id.animal_age);
            relation = itemView.findViewById(R.id.animal_relation);
            editAnimal = itemView.findViewById(R.id.editTV);

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View animalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(animalView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        // Set data
        final Animal animal = animalList.get(position);

        holder.tagNumber.setText(animal.getTagNumber());
        holder.breed.setText(animal.getBreed());
        holder.weight.setText(animal.getWeight());
        holder.age.setText(animal.getAge());
        holder.relation.setText(animal.getRelation());

        holder.editAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open EditActivity
                Intent editAnimalIntent = new Intent(mContext, EditAnimalActivity.class);
                editAnimalIntent.putExtra("tagNumber", animal.getTagNumber());
                editAnimalIntent.putExtra("breed", animal.getBreed());
                editAnimalIntent.putExtra("weight", animal.getWeight());
                editAnimalIntent.putExtra("age", animal.getAge());
                editAnimalIntent.putExtra("relation", animal.getRelation());
                editAnimalIntent.putExtra("animalID", animal.getAnimalID());

                mContext.startActivity(editAnimalIntent);
            }
        });
    }

    @Override
    public int getItemCount(){
        // Dataset size
        return animalList.size();
    }
}
