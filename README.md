# Better

Welcome to Better, An android application, where users can make a report (post) like potholes on road, garbage mismanagement, flooded roads,corruption faced, etc and view, like, and comment on similar reports(posts) as well.
There are several features that make this app very unique and highly practial and scalable and aims to make the world a **better** place, so is its name.

download apk : https://drive.google.com/file/d/12YNkkzEVfg36DNPLQzuTbLxZTOyYmFKs/view?usp=sharing

![Better Icon](./Images/better%20dark%20cropped.png)

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

### Offline
The app runs the Machine learning models locally using the phones processing power thanks to tensorflow lite. Which lets the use of machine learning models implemented in the client side to run completly offline

### Social Media 
The app is mainly a reporting app, where people report publicly/anonymously, the issuses that they want to be resolved. But this also acts as a social mdeia platform, where other people can see your reports and you can see other people s reports and provide your opinion, your voice, and your validation by using feature like Like, Comments, etc that gets updated in real time. Based on your activities you get social points which are displayed along with your val points. This enables a platform for a set of people and ecourages the act of raising issues and resolving it, thus making everything a bit **Better than Before** 


### Better campaign
"BETTER" has a unique way of proceeding with developments. There is a lot of features, a lot of types of posts that need to be there in the app. Therefore, the features are added in stages, each stage representing a mission in this campaign. More about the stage 1 of Better towards the end

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

### Libraries Used
Apart from the base required libraries from android studio

-   [Foundation](https://developer.android.com/jetpack/components)  - Components for core system capabilities and support for multidex and automated testing.
    -   [AppCompat](https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat)  - Degrade gracefully on older versions of Android.
    -   [Test](https://developer.android.com/training/testing/)  - An Android testing framework for unit and runtime UI tests.
-   [Architecture](https://developer.android.com/jetpack/arch/)  - A collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence.
    -   [Data Binding](https://developer.android.com/topic/libraries/data-binding/)  - Declaratively bind observable data to UI elements.
    -   [Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle)  - Create a UI that automatically responds to lifecycle events.
       -   [Threads](https://developer.android.com/reference/java/lang/Thread)  - For multithreading, Doing synchronous activities like loading images 
    -   [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)  - Build data objects that notify views when the underlying database changes.
    -   [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/)  - Handle everything needed for in-app navigation.

    -   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)  - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.
    -   [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)  - Manage your Android background jobs.
-   [UI](https://developer.android.com/guide/topics/ui)  - Details on why and how to use UI Components in your apps - together or separate
    -   [Animations & Transitions](https://developer.android.com/training/animation/)  - Move widgets and transition between screens.
     -   [Recycler View](https://developer.android.com/guide/topics/ui/layout/recyclerview)  - Move widgets and transition between screens.
    -   [Fragment](https://developer.android.com/guide/components/fragments)  - A basic unit of composable UI.
    -   [Layout](https://developer.android.com/guide/topics/ui/declaring-layout)  - Lay out widgets using different algorithms.
   -   Third party and miscellaneous libraries
    -   [Tensorflow lite](https://www.tensorflow.org/lite/tutorials/model_maker_image_classification)  Using Mahcine learning models locally in phone
    -   [Firabse](https://firebase.google.com/): for  [Real time database](https://firebase.google.com/docs/database)
    -   [Lottie files](https://lottiefiles.com/)  To use lottie file, json formated animations

## Machine Learning 

### Information
As mentioned above Machine Learning is a core feature of this application that gives it a unque yet special advandtage over anything else.
I have 3 models of Image clasiffication implemented within the app.
The models are made using pre trained deep learning models for image classificatinon. Tensorflow Tensorflow Lite Model Maker specifically desinged to make models to run natively in android. 

## Models so far

 #### POTHOLES DETECTION
#### About
The main aim of this is to detect the road with potholes or without potholes

#### Input
The app takes the image of a Road as an input
#### Output
The app gives confidence probability of 

 1. is a pothole
 2. is not a pothole

This probability can be used to give confidence(Machine level validation ) to a report (post) on pothole 

 #### Dataset used
Pothole Detection Dataset
https://www.kaggle.com/atulyakumar98/pothole-detection-dataset

 #### Code
This is my code :
https://www.kaggle.com/shubhaprasad/pothole-image-classification

#### Results
Training Accuracy : 100%
Testing Accuracy : 100%

#### Application
The probability obtained as output after providing the required image as input can be used to give confidence(Machine level validation ) to a report (post) on Potholes type


 ### WASTE DETECTION
#### About
The main aim of this is to get the probability of wheather an image is Waste (Recyclable) or not Waste (Organic) 

#### Input
The app takes the image of object/ objects as an input
#### Output
The app gives confidence probability of 

 1. is waste
 2. is organic

This probability can be used to give confidence(Machine level validation ) to a report (post) on pothole 

 #### Dataset used
Waste Classification Data
https://www.kaggle.com/techsash/waste-classification-data

 #### Code
This is my code :
https://www.kaggle.com/shubhaprasad/waste-organic-recyclable

#### Results
Training Accuracy : 100%
Testing Accuracy : 100%

#### Application
The probability obtained as output after providing the required image as input can be used to give confidence(Machine level validation ) to a report (post) on Garabge type

 ### ROADWAY FLOODING DETECTION
#### About
The main aim of this is to get the probability that an image is of Roadway flooding

#### Input
The app takes the image of road as an input
#### Output
The app gives confidence probability of 
Roadway floods

This probability can be used to give confidence(Machine level validation ) to a report (post) on pothole 

 #### Dataset used
Roadway Flooding Image Dataset
https://www.kaggle.com/saurabhshahane/roadway-flooding-image-dataset

 #### Code
This is my code :
https://www.kaggle.com/shubhaprasad/roadwayflood

#### Results
Training Accuracy : 100%
Testing Accuracy : 100%
#### Application
The probability obtained as output after providing the required image as input can be used to give confidence(Machine level validation ) to a report (post) on Road Flood type

# Application

## Images



### App ScreenShots 

![splashscreen](./Images/app%20screenshots/1r.jpg?raw=true "Title")          ![Home Screen](./Images/app%20screenshots/2r.jpg?raw=true "Title")

![Report View / Comments](./Images/app%20screenshots/4r.jpg?raw=true "Title")          ![Menu Screen 1](./Images/app%20screenshots/5r.jpg?raw=true "Title")

![Menu Screen 2](./Images/app%20screenshots/6r.jpg?raw=true "Title")          ![Sign In](./Images/app%20screenshots/8r.jpg?raw=true "Title")

![Make Report Screen](./Images/app%20screenshots/11r.jpg?raw=true "Title")          ![User / User Posts / User Comments](./Images/app%20screenshots/13r.jpg?raw=true "Title")

![Sign Out](./Images/app%20screenshots/7r.jpg?raw=true "Title")
