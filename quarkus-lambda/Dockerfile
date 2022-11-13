FROM  public.ecr.aws/lambda/java:11

ADD target/quarkus-lambda-1.0.0-SNAPSHOT-runner.jar /var/task/lib/quarkus-lambda.jar
ADD target/lib/  /var/task/lib/

CMD ["io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest"]
