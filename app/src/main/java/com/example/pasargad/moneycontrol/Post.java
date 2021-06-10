package com.example.pasargad.moneycontrol;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

import androidx.emoji2.text.flatbuffer.FlexBuffers;

public class Post {

    public String date;
    public String details;
    public String income;
    public String price;
    public String title;
    public int startCount=0;
    public Map<String ,Boolean> stars=new HashMap<>();
    private DatabaseReference registerRef;
    public Post(){

    }

    public Post(String date, String details, String income, String price, String title) {
        this.date = date;
        this.details = details;
        this.income = income;
        this.price = price;
        this.title = title;
    }

    public Map<String, Object> toMap() {
        HashMap<String,Object> result=new HashMap<>();
        result.put("details",details);
        result.put("income",income);
        result.put("date",date);
        result.put("price",price);
        result.put("title",title);
        result.put("startCount",startCount);

        return result;
    }
    public void writeNewPost(String userId, String title){
        String key=registerRef.child("transactions").push().getKey();
        Post post=new Post();
        Map<String,Object> postValues=post.toMap();

        Map<String,Object> childUpdates=new HashMap<>();
        childUpdates.put("transactions"+key,postValues);

        registerRef.updateChildren(childUpdates);
    }

}
