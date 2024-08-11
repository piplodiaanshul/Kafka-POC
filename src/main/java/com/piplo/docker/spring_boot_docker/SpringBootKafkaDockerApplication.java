package com.piplo.docker.spring_boot_docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SpringBootKafkaDockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaDockerApplication.class, args);
	}

}
