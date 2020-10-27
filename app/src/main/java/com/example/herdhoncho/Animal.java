package com.example.herdhoncho;

import com.google.firebase.database.Exclude;

public class Animal {

    String tagNumber, year, breed, weight, imageUrl;

    @Exclude
    String animalID;

    public Animal(){
        // Required for Firebase
    }

    public Animal(String tagNumber, String year, String breed, String weight, String imageUrl){
        this.tagNumber = tagNumber;
        this.year = year;
        this.breed = breed;
        this.weight = weight;
        this.imageUrl = imageUrl;
    }

    @Exclude
    public String getAnimalID(){
        return animalID;
    }

    @Exclude
    public void setAnimalID(String animalID){
        this.animalID = animalID;
    }

    public String getTagNumber(){
        return tagNumber;
    }

    public void setTagNumber(String tagNumber){
        this.tagNumber = tagNumber;
    }

    public String getYear(){
        return year;
    }

    public void setYear(String year){
        this.year = year;
    }

    public String getBreed(){
        return breed;
    }

    public void setBreed(String breed){
        this.breed = breed;
    }

    public String getWeight(){
        return weight;
    }

    public void setWeight(String weight){
        this.weight = weight;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

}