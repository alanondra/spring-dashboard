package com.aondra.dashboard;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration
{
	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer()
	{
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.8-alpine3.20"));
	}

	@Bean
	@ServiceConnection
	RabbitMQContainer rabbitContainer()
	{
		return new RabbitMQContainer(DockerImageName.parse("rabbitmq:4.0-rc-alpine"));
	}

	@Bean
	@ServiceConnection(name = "redis")
	GenericContainer<?> redisContainer()
	{
		return new GenericContainer<>(DockerImageName.parse("redis:7.4.0-alpine3.20")).withExposedPorts(6379);
	}
}
