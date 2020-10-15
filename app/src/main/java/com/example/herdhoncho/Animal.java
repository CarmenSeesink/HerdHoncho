package com.example.herdhoncho;

import com.google.firebase.database.Exclude;

public class Animal {

    String tagNumber, breed, age, weight, relation;

    @Exclude
    String animalID;

    public Animal(){
        // Required for Firebase
    }

    public Animal(String tagNumber, String breed, String age, String weight, String relation){
        this.tagNumber = tagNumber;
        this.breed = breed;
        this.age = age;
        this.weight = weight;
        this.relation = relation;
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

    public String getBreed(){
        return breed;
    }

    public void setBreed(String breed){
        this.breed = breed;
    }

    public String getAge(){
        return age;
    }

    public void setAge(String age){
        this.age = age;
    }

    public String getWeight(){
        return weight;
    }

    public void setWeight(String weight){
        this.weight = weight;
    }

    public String getRelation(){
        return relation;
    }

    public void setRelation(String relation){
        this.relation = relation;
    }

}