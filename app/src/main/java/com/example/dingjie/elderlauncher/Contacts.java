package com.example.dingjie.elderlauncher;

import android.graphics.drawable.Drawable;

/**
 * Created by dingjie on 2018/3/18.
 */

public class Contacts {
    private String name = "";
    private String number = "";
    private Drawable image = null ;
    Contacts(String name ,String number , Drawable image){
        this.name = name;
        this.number = number;
        this.image = image;
    }
    Contacts(String name ,String number ){
        this.name = name;
        this.number = number;
    }
    public String getName(){
        return name;
    }
    public String getNumber(){
        return number;

    }
    public Drawable getImage(){
        return image;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setNumber(String number){
        this.number = number;
    }
    public void setImage(Drawable image){
        this.image = image;
    }

}
