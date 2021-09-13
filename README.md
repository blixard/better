# Better

Welcome to Better, An android application, where users can make a report (post) like potholes on road, garbage mismanagement, flooded roads,corruption faced, etc and view, like, and comment on similar reports(posts) as well.
There are several features that make this app very unique and highly practial and scalable and aims to make the world a **better** place, so is its name.

## Features

### Machine Learning
The app uses different Machine learning models to give a machine level validation to each individual reports. Curently the application has 3 deep learning models within the app providing validation(confidence ) which I have explained in much details later in this.
These help classify the important/alarming reports to seek better attentions.

The Machine Learning models can be separately used as a testing Interface, only for the development purpose.  

### Location
While posting a report the application takes the cordinates of the report as well as a picture. The cordinates are synced on a map for reports like potholes and flooded roads which can help avoiding unfortunated circumstances. 

With the cordinates with us, The respective reports can be shared to the respective departments of that location that are responsible for resolving the particular issue.

### Val Points
Every post has cordinates and post type associated with it , which can be observed by a machine to catagorize similar reports. The app uses this to calulate a score for every reports individually give them a value that I call val points.If multiple users have similar reports then those reports will have a higer val points comparatively. This val points along with confidence (Machine level validation using Machine learning models ) gives a unique value to each report which classifies the important and legit reports that requires more attention and does justice to those.

### Incentives
Based on the confidence and val points of  a report made by the user a particulare point is determined for the user for that particular report. After the report is resolved the user gets the respective points for that report as well as points for post validation of the report as it gets verified by the particulare departments and experts. The points can be negative as well. So the user should not try to cheat the system and find loop holes the trained models unless it is to help in the development process. The points then lated can be exchanged for items and shows a status among all other users.

### Real time data
The app uses firebase real time databse to store the posts, comments and user information. So, Everything gets updated in real time providing an interface for liking and commenting and viewing other posts.

### Multi Language
The app supports multi language, local languages. Making it accessible to a much larger audience.

### Social Media 
The app is mainly a reporting app, where people report publicly/anonymously, the issuses that they want to be resolved. But this also acts as a social mdeia platform, where other people can see your reports and you can see other people s reports and provide your opinion, your voice, and your validation by using feature like Like, Comments, etc that gets updated in real time. Based on your activities you get social points which are displayed along with your val points. This enables a platform for a set of people and ecourages the act of raising issues and resolving it, thus making everything a bit **Better than Before** 

# Development

## Android
### Information
The Folder betterlife in this repository contains the code base for the entire application. 
IDE used is android studio 
Language used is Java
### Getting Started
This repository contains the code base for the android application.
To get started with the developent of this application,

 1. Install Android Studio
Here is a link to help you with that https://developer.android.com/studio/install
 2. Clone this Repository
 3. Open project in Android studio and selct the betterlife folder available in this repository
 4. Let Build gradle do the world for you in setting up the project , and now you are good to go. 

### Dependencies
#### Project level 

  classpath "com.android.tools.build:gradle:7.0.0"  
  classpath 'com.google.gms:google-services:4.3.10'  
  
#### App level
implementation 'androidx.appcompat:appcompat:1.3.1'  
implementation 'com.google.android.material:material:1.4.0'  
implementation 'com.google.android.gms:play-services-auth:19.2.0'  
implementation 'androidx.constraintlayout:constraintlayout:2.1.0'  
implementation 'com.google.firebase:firebase-database:20.0.1'  
implementation 'org.tensorflow:tensorflow-lite-support:0.1.0'  
implementation 'org.tensorflow:tensorflow-lite-metadata:0.1.0'  
implementation 'com.google.android.gms:play-services-location:18.0.0'  
implementation 'com.google.firebase:firebase-auth:21.0.1'  
def lottieVersion = "3.4.0"  
implementation "com.airbnb.android:lottie:$lottieVersion"  
testImplementation 'junit:junit:4.+'  
androidTestImplementation 'androidx.test.ext:junit:1.1.3'  
androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
