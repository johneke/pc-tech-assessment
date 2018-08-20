Sample invocation:

java -Dsource="https://s3.amazonaws.com/je-randomfiles/assignment_data_full.json" -jar EventServer-0.0.1-SNAPSHOT.jar
java -Dsource="https://s3.amazonaws.com/je-randomfiles/assignment_data_short.json" -jar EventServer-0.0.1-SNAPSHOT.jar

Drawbacks:

* I made a cheap (and fairly inefficient) data abstraction layer. I did not want to bother with a database, but the result is that the EventDataService might look a bit messy/hacky
* I did not add any tests. Would have been nice to have some, but decided to focus the limited time on other areas
* The larger data set could have been loaded faster if I had downloaded a compressed version and uncompressed in memory.
* A lot of the components in the front end code have differing patterns. I knew what I wanted to do, but I just did not have the time to figure/debug it out.