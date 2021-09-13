package com.example.betterlife;

import android.net.Uri;
import java.io.Serializable;

public class Users implements Serializable  {
    public String personName;
    public String personGivenName;
    public String personFamilyName;
    public String personEmail;
    public String personId;
    public String personPhoto;
    public String posts = "";
    public String comments = "";

    public Users(){
    }
    public Users(String personName, String personGivenName, String personFamilyName, String personEmail, String personId , String personPhoto) {
        this.personName = personName;
        this.personGivenName = personGivenName;
        this.personFamilyName = personFamilyName;
        this.personEmail = personEmail;
        this.personId = personId;
        this.personPhoto = personPhoto;
    }

    public void setPosts(String posts){
        this.posts = posts;
    }
    public void setComments(String comments){
        this.comments = comments;
    }
}
