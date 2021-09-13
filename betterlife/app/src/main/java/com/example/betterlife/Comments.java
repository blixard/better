package com.example.betterlife;

public class Comments {
    public String post;
    public String user;
    public String comm;
    public String profilePicture;
    public String postCount;
    public Comments(){}
    public Comments(String user, String comm, String post, String profilePicture, String postCount ){
        this.user = user;
        this.comm = comm;
        this.post = post;
        this.profilePicture = profilePicture;
        this.postCount = postCount;
    }
}
