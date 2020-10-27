package com.example.herdhoncho;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class AnimalsAdapter extends RecyclerView.Adapter<AnimalsAdapter.ViewHolder> {

    private final ArrayList<Animal> animalList;
    private final Context mContext;

    public AnimalsAdapter(ArrayList<Animal> animalsList, Context mContext){

        this.animalList = animalsList;
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tagNumber, year, breed, weight, editAnimal, deleteAnimal;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tagNumber = itemView.findViewById(R.id.animal_tagNumber);
            year = itemView.findViewById(R.id.animal_year);
            breed = itemView.findViewById(R.id.animal_breed);
            weight = itemView.findViewById(R.id.animal_weight);
            editAnimal = itemView.findViewById(R.id.editTV);
            deleteAnimal = itemView.findViewById(R.id.deleteTV);
            imageView = itemView.findViewById(R.id.image_view_upload);
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
        holder.year.setText(animal.getYear());
        holder.breed.setText(animal.getBreed());
        holder.weight.setText(animal.getWeight());
//        holder.relation.setText(animal.getRelation());

        // Retrieve image
        Picasso.with(mContext)
                .load(animal.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.editAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open EditActivity
                Intent editAnimalIntent = new Intent(mContext, EditAnimalActivity.class);
                editAnimalIntent.putExtra("tagNumber", animal.getTagNumber());
                editAnimalIntent.putExtra("year", animal.getYear());
                editAnimalIntent.putExtra("breed", animal.getBreed());
                editAnimalIntent.putExtra("weight", animal.getWeight());
                editAnimalIntent.putExtra("animalID", animal.getAnimalID());

                mContext.startActivity(editAnimalIntent);
            }
        });

        holder.deleteAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAnimalDialog(animal.getAnimalID());
            }
        });
    }

    private void showDeleteAnimalDialog(final String animalID) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
        builder.setMessage("Are you sure you want to delete this animal?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((LivestockActivity)mContext).deleteAnimalFromFirebase(animalID);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount(){
        // Dataset size
        return animalList.size();
    }
}
