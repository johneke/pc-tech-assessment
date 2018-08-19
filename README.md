Sample invocation:

java -Dsource="https://s3.amazonaws.com/je-randomfiles/assignment_data_full.json" -jar EventServer-0.0.1-SNAPSHOT.jar
java -Dsource="https://s3.amazonaws.com/je-randomfiles/assignment_data_short.json" -jar EventServer-0.0.1-SNAPSHOT.jar

Drawbacks:

* I made a cheap (and fairly inefficient) data abstraction layer. I did not want to bother with a database, but the result is that the EventDataService might look a bit messy/hacky
