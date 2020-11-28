FROM adoptopenjdk/openjdk11 as build
WORKDIR /home/emanagement
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
RUN ./gradlew dependencies
COPY src src
RUN ./gradlew build -x test
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

FROM openjdk:11-jre-slim
MAINTAINER 'Cihangir Ozmus "cozmus@gmail.com"'
LABEL description="Image for emanagement with java11"
VOLUME /tmp
ARG DEPENDENCY=/home/emanagement/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /home/emanagement/lib
COPY --from=build ${DEPENDENCY}/META-INF /home/emanagement/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /home/emanagement
RUN mkdir -p home/uploads
EXPOSE 8080
ENTRYPOINT ["java","-cp","home/emanagement:home/emanagement/lib/*","com.cozmus.emanagement.EmanagementApplication"]