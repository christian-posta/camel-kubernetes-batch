# File Batch Processing with Apache Camel and Kubernetes

steps:

## Set up initial file processing

> mkdir -p /tmp/camel/incoming
> mkdir -p /tmp/camel/outgoing
> echo "<hello/>" >> /tmp/camel/incoming/foo.txt
> export INCOMING_FILE_PATH=/tmp/camel/incoming
> export OUTGOING_FILE_PATH=/tmp/camel/outgoing

### startup AMQ 


> ./bin/amq

Start up the route:

> cd file-ingress-events
> mvnnt exec:java


Check that the file was processed (moved to `/tmp/camel/outgoing` and that a message was enqueued to the broker with the file location as the body

### Start up the job 

Foo

> cd file-backend-job
> export JOB_FILE_PATH=/tmp/camel/outgoing
> export JOB_FILE_NAME=foo.txt
> mvnnt exec:java

Foo
 
