
# Evently App 📅📌
Rest API developed as Final Project server side for Factoria F5 FullStack Web Development Bootcamp.

This API manages all information required to develop a small social network to help users enhance bonds with their community by sharing events and interacting with those of their interest by uploading, joining and unjoining them and add details such as tags, requirements or images. It also provides a notification service to warn participants about its joined events' modifications by sending an email and a notification in their application.

## Documentation
[Evently API](http://localhost:8080/swagger-ui/index.html#/)

### Client
+ [Evently App](https://github.com/xcapde/eventlyUI)

## Tech Stack
+ Java
+ Spring Boot
+ JPA Repository
+ H2 console
+ Lombok
+ Cloudinary
+ Mail Sender
+ JUnit Mockito
+ Swagger

### Version control
+ GIT
+ Git Hub

### Development tools
+ IntelIJ
+ Postman

## Objectives
The objectives of this project were described by the group teammates and all the functional requirements were divided in sprints which were developed by importance using Agile Methodologies as Kanban and Scrum.

Being the Event the main entity of the application our first objective was to display a list of all Events, allow logged Users to create new ones, update or delete them. Every Event should contain a title, description, location, date and its publisher. Moving on with our requirements, the next one was to be able to add a location to them. Depending on Event type (offline or online) Users should be able to add an Adress in offline Events and a URL in online Events.
Continuing with the application features, we thought about adding details such as Tags, Requirements and Images to Events. Each of these details should be able to be seen by everyone and created and deleted by the Event Publisher.

Once we got satisfied with the Events we moved to the next feature, to allow Users to join or unjoin Events by becoming  participants and show the User if they are participating or not. Logged Users should be able to see if they joined an Event and not logged Users should appear as non-participants.

To help Users find their target Events, we added a seacher so Users could look for Events by its Tags, location, title, description, date or type.

Since one of the aims of our project was to ease User's social life we decided to add a week calendar in Users' profile so Users can see those Events they joined planned for the week.

Lastly we thought it would be a shame for Users to miss if Events had been modified or deleted so we thought about notifing Event participants of those changes by sending an email on each modification and display a Notification in our application. This Notification could be seen in User's profile on Desktop version and in Notifications in Mobile version. Each of this Notifications should be able to be deleted or to be modified by checking or unchecking them.

Given the deadline of the project (four weeks time) this project turns out to be unfinished but we are looking forward to add new features and end those which are not finished yet.

### Relationship entity diagram
![App Screenshot](https://res.cloudinary.com/dkju5on5v/image/upload/v1664302749/Evently_Entity_Diagram_1_lad8ix.png)
## Features
### Event CRUD
+ Display all Events
+ Display the detail of a single Event
+ Create an Event
+ Update an Event
+ Delete an Event
+ Search Events by location, description, title, date or Tag
+ Display Users' joined Events
+ Display Users' published Events

### Join CRUD
+ Create a Participation (between authenticated User and Event)
+ Delete a Participation
+ Display if User is participating

### Details CRD
+ Create, delete and display Tags
+ Create, delete and display Requirements
+ Create, delete and display Images

### Notification CRUD
+ Create a Notification (when the Event is deleted or modified)
+ Display all Notifications
+ Delete a Notification
+ Update a Notification (checking or uncheking it)

### User CR
+ Create a User (sign up)
+ Display Users
+ Authenticate User (log in)

## Next
Since we've just got started we are planning to add a lot of new features to our application .

In order to avoid User looking for the addres we'd like to add a map to spot Event location on its detail page. Following with this feature we'd like to fetch address data from a location API and use it in our location form to ensure the adress is real. It could be also cool to add realtime weather in Event detail page keeping in mind the idea of easing User's experience when using the app.

On the User side we thougth about updating Users data by allowing them to upload avatars, banners and modify their information in their profile.

One of the features we've been thinking about since the first day is to let Users upload their Social Proof of the Events they participated in so they can rate the Events and other Users can take this information into account to decide wether to join an Event or not. It is also important for Event publisher to have a feedback about its Event and how it went.

Following with the User side we'd like to add volunteering so those who want to actively participate in Event organization can do so.

Finally, we'd like to add a reminder when User has an incoming Event.

We'd like to add many more features, some of them are: groupal chats so participants can chat about the Events, giving the User the ability to save an Event without joining it,  print the details and following User accounts.

We expect many more features will come on the go.

## Authors
+ [Xavier Capdevila](https://github.com/xcapde)
+ [Laura Parra](https://github.com/lauraparra00)
+ [Joel Blasi](https://github.com/JoelBlasi)
+ [Robert Camara](https://github.com/rocailos)
+ [Agnès Font](https://github.com/afonttorres)

## Installation
This project was bootstrapped with Spring Boot and might require dependencies. Make sure to check your buil.gradle file to install them. 

#### 1. Clone the repository
```bash
 https://github.com/afonttorres/eventlyAPI.git
```


#### 2. Run the app in development mode in your IDE