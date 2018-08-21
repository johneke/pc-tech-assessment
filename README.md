# Tools Required

* Git
* Java 8* JDK
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
