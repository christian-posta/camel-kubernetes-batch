Run as docker container:


> docker run -it --rm --name camel -v /opt/camel/foo:/opt/camel -e FILE_NAME=foo.txt -e FILE_PATH=/opt/camel fabric8/file-backend-job:1.0-SNAPSHOT