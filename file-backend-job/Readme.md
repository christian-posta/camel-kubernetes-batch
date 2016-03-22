Run as docker container:

mvn clean install docker:run

(note, this does not generate a kubernetes json)


> docker run -it --rm --name camel -v /opt/camel/foo:/opt/camel -e FILE_NAME=foo.txt -e FILE_PATH=/opt/camel fabric8/file-backend-job:1.0-SNAPSHOT