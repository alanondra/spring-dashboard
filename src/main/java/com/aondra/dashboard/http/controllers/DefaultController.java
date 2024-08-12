package com.aondra.dashboard.http.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController extends AbstractController
{
	@GetMapping("/")
	public String index()
	{
		return "hello, world";
	}
}
