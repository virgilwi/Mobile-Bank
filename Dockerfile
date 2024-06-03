# Используем образ с OpenJDK 17
FROM openjdk:17-jdk-slim

# Установка Gradle
ARG GRADLE_VERSION=7.4
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp && \
    unzip /tmp/gradle-${GRADLE_VERSION}-bin.zip -d /opt && \
    ln -s /opt/gradle-${GRADLE_VERSION} /opt/gradle && \
    rm /tmp/gradle-${GRADLE_VERSION}-bin.zip && \
    apt-get remove -y wget unzip && \
    apt-get autoremove -y && \
    apt-get clean

# Добавляем Gradle в PATH
ENV GRADLE_HOME=/opt/gradle
ENV PATH=${GRADLE_HOME}/bin:${PATH}

# Создаем рабочую директорию
WORKDIR /app

# Копируем текущую директорию в контейнер
COPY . .

RUN gradle build
RUN gradle test

# Указываем команду для запуска Gradle сборки
CMD ["gradle", "bootRun"]
