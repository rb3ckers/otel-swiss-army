plugins {
  id("java")
  id("org.springframework.boot") version "3.0.6"
  id("io.spring.dependency-management") version "1.1.0"
  id("com.google.cloud.tools.jib") version "3.3.2"
}

sourceSets {
  main {
    java.setSrcDirs(setOf("."))
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("io.opentelemetry:opentelemetry-api")
}

jib {
  from {
    image = "eclipse-temurin:21-jre"
  }
  to {
    image = "ghcr.io/rb3ckers/dice-roller-app:java"
  }
  container {
    mainClass = "otel.DiceApplication"
    ports = listOf("8080")
  }
}
