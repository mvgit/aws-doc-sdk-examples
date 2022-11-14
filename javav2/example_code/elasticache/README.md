Install this jar file to local maven repo using following command. This is required to include the dependency for this project.

mvn install:install-file -Dfile=src/main/resources/AmazonElastiCacheClusterClient-1.2.0.jar -DgroupId=com.ssl.elasticache -DartifactId=ssl_elasticache -Dversion=1.0.0 -Dpackaging=jar


We are using maven-shade-plugin to create an Executable JAR for the project, including its dependencies. 