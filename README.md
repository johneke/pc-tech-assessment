# Tools Required

* Git
* Maven

# Steps To Run

1. Clone this repo
2. `cd /path/where/repo/was/cloned`
3. `mvn clean install`
4. See "Sample Invocations" section


# Sample Invocations
First make sure you are in the directory where you cloned this repo. Then...

```
java -Dsource="https://s3.amazonaws.com/je-randomfiles/assignment_data_full.json" -jar target/EventServer-0.0.1-SNAPSHOT.jar
```

Or..

```
java -Dsource="https://s3.amazonaws.com/je-randomfiles/assignment_data_short.json" -jar target/EventServer-0.0.1-SNAPSHOT.jar
```


You should now have the back end running without the front end. To deploy the front end into the back end, run (node, must build front end first. See here on how to clone and build front end code: https://github.com/johneke/pc-tech-assessment-client):

```
cp -R /path/to/frontend/repo/dist/events-viewer/* /path/to/backend/repo/src/main/resources/static/
cd /path/to/backend/repo/
mvn clean install
java -Dsource="https://s3.amazonaws.com/je-randomfiles/assignment_data_full.json" -jar target/EventServer-0.0.1-SNAPSHOT.jar
```

# Couple of Notes

* I made a cheap (and fairly inefficient) data abstraction layer on the backend. I did not want to bother with a database, but the result is that the EventDataService might look a bit messy/hacky
* I did not add any tests. Would have been nice to have some, but decided to focus the limited time on functionality
* The larger data set could have been loaded faster if I had downloaded a compressed version and uncompressed in memory.
* A lot of the components in the front end code have differing patterns. I knew what I wanted to do, but I just did not have the time to figure out what the right patterns are.
