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

You should now have the API server running.

# Couple of Notes

* I made a cheap (and fairly inefficient) data abstraction layer on the backend. I did not want to bother with a database, but the result is that the `EventDataService` might look a bit messy/hacky/inefficient.
* I did not add any tests. Would have been nice to have some, but decided to focus the limited time on functionality.
* Cold start time could be faster if I fetched a zipped version from S3 and unzipped in memory, however I decided not to get too fancy with this.
