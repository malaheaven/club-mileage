rm -rf ./docker/db/
./gradlew clean
./gradlew bootJar
./gradlew bootJar

cd docker/

#sh docker-clear.sh
sh docker-start.sh
