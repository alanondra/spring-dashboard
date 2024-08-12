package com.aondra.dashboard;

import org.springframework.boot.SpringApplication;

public class TestDashboardApplication
{
	public static void main(String[] args)
	{
		SpringApplication.from(Application::main).with(TestcontainersConfiguration.class).run(args);
	}
}
