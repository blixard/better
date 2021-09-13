package com.example.betterlife;

import java.io.Serializable;

public class Posts implements Serializable {
    public String id;
    public String username;
    public String title;
    public String post_type;
    public String description;
    public Double confidence;
    public String lattitude;
    public String longitude;
    public String userId;
    public String likes="";
    public int valiance=0;
    public int comments = 0;
//    int likes=0;
//    int valiance=0;
    public Posts(){
    }

    public Posts(String id, String username,String title, String post_type, String description , double confidence, String lattitude, String longitude, String userID) {
        this.id  = id;
        this.username = username;
        this.title = title;
        this.post_type = post_type;
        this.description = description;
        this.confidence = confidence;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.userId = userID;
    }

    public void setLikes(String likes){
        this.likes = likes;
    }

}
