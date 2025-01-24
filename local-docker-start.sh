./gradlew clean test -i
./gradlew clean jibDockerBuild
docker-compose -p mediasoft-test up -d