# Interrogating Dead Docker Container

Just keeping a note of this because it's really cool!

docker commit $CONTAINER_ID test_image
docker run -it --entrypoint=sh test_image

This allows us to look inside a container which has stopped, look at files, even rerun commands!
