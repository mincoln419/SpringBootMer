./gradlew bootRun -Pargs=--spring.batch.job.names=helloJob

./gradlew bootJar

java -jar build/libs/mermer-batch-0.0.1-SNAPSHOT.jar --spring.batch.job.names=helloJob