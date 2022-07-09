docker stop $(docker ps -aq);
docker rm $(docker ps -aq);
docker-compose down --rmi all

#docker rmi $(docker images -aq) --force;
#docker system prune --force;
#docker system prune --volumes --force;
