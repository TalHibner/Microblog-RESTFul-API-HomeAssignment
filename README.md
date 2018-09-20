# Microblog-RESTFul-API-Assignment

Specifications

Micro blog will have simple text as news posts.

We want to create a RESTful JSON API to handle the post resource (create, update, read, etc). 
The service accepts json requests and responds with a json response.

This system will also support upvoting and downvoting a post.
The posts and votes should be saved in a storage engine of your choice.

Web service (RESTFul API)
This service will allow the following actions:
1.      A user can create a new post providing its text.

2.      A user can update an existing post’s text.

3.      A user can upvote or downvote a post, but only once.

4.      A user can request for a list of “Top Posts”.
Top posts should be determined by a combination of upvotes and creation time of a post.

“Top Posts” list is being asked thousands of time per second and the number of posts in the database may be very high.
A simple blog created for an home assignment apart of an interiew process.

Technologies/Frameworks I used:

Spring-bot, JPA.
Hibernate, MySQL database.
JDK 8, Maven.
Docker, Docker-compose.
Web server, such as Apache Tomcat 8.0 (tested only on Tomcat, probably works on other web servers too, maybe requires minor modifications).
Running on Windows or Mac.

Features:

Users must add his user-name to the Json in the request body when sending an API requrest.
User can create posts, update posts or completely delete it from the database:
Post request - /post/create with Json in the body(id, title, content, username)
Put request - /post/{id}/update with Json in the body (id, title, content, username)
Delete request - post/{id}/delete with Json in the body (username)

Users can like (+1) and dislike (-1) posts and comments:
Post request - post/{id}/like with Json in the body (username)
Post request - post/{id}/dislike with Json in the body (username)

List of 10 latest posts and TOP 10 posts (by user rating sum and date) in the right column:
Get request - /post/top


#Installation

I have been dockerizing the service (REST API and the storage engine) by formatting into a docker-compose YAML file.
The docker-compose YAML file define the two different docker containers for the REST API and the storage engine.

run on unix the run.sh script or Run the commands:
mvn clean install
docker-compose -f docker-compose.yml up --build --force-recreate -d

The mysql db tables will be created automaticaly ( using Hibernate) when the doker containers are up.

Note: If running on Unix machine you need to run the run.sh so everything wil go up.
Troubleshooting for windows: 
    1.when using Windows please change in docker-compose.yml the networks driver from "bridge" to "nat"  
    2.please Check the "experimental" option in docker deamon options in docker settings.
    3.change COPY line in the Dockerfile to COPY MicroblogRest-0.0.1-SNAPSHOT.jar /usr/microblog
      (specify the exect file and erase the forword slash from target path
    4.add db-init folder manually to the project folder MicroblogRest.
