# java-webcrawler project

Webcrawler project to fetch URLs from a given URL.

## Instructions

This project was constructed using Maven to build it and manage dependencies. I used Jsoup to fetch the URLs when crawling webpages and sqlite3 database to store and retrive the unique URLs fetched.
* User input variables in terminal:
* MAX_DEPTH = Limit the max depth of the loop
* URL = URL to start the crawling

## Execution

After cloning the repository, go to main directory and execute the following Maven and Java commands to build (with all dependencies in same .jar) and run the project:
* mvn clean compile assembly:single (build the jar)
* java -cp target\webcrawler-project-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.dougdg.webcrawler.controller.Controller (run the app)

After running the last command, you will be asked to provide a URL and a number, which will control the depth of the recursion thru fetched URLs. 

When it finished fetching all URLs, automatically the program will store the unique URLs in the sqlite3 database and retrieve all of them into the screen.
