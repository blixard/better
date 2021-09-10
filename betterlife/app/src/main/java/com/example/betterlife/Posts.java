package com.example.betterlife;
public class Posts {
    public String username;
    public String title;
    public String post_type;
    public String description;
//    int likes=0;
//    int valiance=0;
    public Posts(){

    }

    public Posts(String username,String title, String post_type, String description) {
        this.username = username;
        this.title = title;
        this.post_type = post_type;
        this.description = description;
    }
}
