function Write-File {
    param([string]$Path, [string]$Content)
    $Dir = Split-Path $Path
    if (!(Test-Path $Dir)) {
        New-Item -ItemType Directory -Path $Dir -Force | Out-Null
    }
    Set-Content -Path $Path -Value $Content -Force
    Write-Host "OK: $Path" -ForegroundColor Green
}

# Create folders
New-Item -ItemType Directory -Path "backend/src/main/java/com/quizapp/controller" -Force | Out-Null
New-Item -ItemType Directory -Path "backend/src/main/java/com/quizapp/service" -Force | Out-Null
New-Item -ItemType Directory -Path "backend/src/main/java/com/quizapp/entity" -Force | Out-Null
New-Item -ItemType Directory -Path "backend/src/main/java/com/quizapp/repository" -Force | Out-Null
New-Item -ItemType Directory -Path "backend/src/main/java/com/quizapp/dto" -Force | Out-Null
New-Item -ItemType Directory -Path "backend/src/main/java/com/quizapp/config" -Force | Out-Null
New-Item -ItemType Directory -Path "backend/src/main/resources" -Force | Out-Null
New-Item -ItemType Directory -Path "frontend/public" -Force | Out-Null
New-Item -ItemType Directory -Path "frontend/src/components" -Force | Out-Null
New-Item -ItemType Directory -Path "frontend/src/styles" -Force | Out-Null

Write-Host "Folders created!" -ForegroundColor Cyan

# pom.xml
Write-File "backend/pom.xml" '<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.quizapp</groupId>
    <artifactId>quiz-platform</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    <name>Quiz Platform</name>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.1.5</version>
        <relativePath/>
    </parent>
    <properties>
        <java.version>17</java.version>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>'

# QuizApplication.java
Write-File "backend/src/main/java/com/quizapp/QuizApplication.java" 'package com.quizapp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuizApplication.class, args);
    }
}'

# CorsConfig.java
Write-File "backend/src/main/java/com/quizapp/config/CorsConfig.java" 'package com.quizapp.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**").allowedOrigins("http://localhost:3000").allowedMethods("*").allowedHeaders("*");
    }
}'

# Quiz.java
Write-File "backend/src/main/java/com/quizapp/entity/Quiz.java" 'package com.quizapp.entity;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int duration;
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
}'

Write-Host "Core files created! Running Maven build..." -ForegroundColor Cyan

