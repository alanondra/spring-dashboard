package com.aondra.dashboard.http.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DefaultController extends AbstractController
{
	@GetMapping("/")
	public ModelAndView index()
	{
		return new ModelAndView("pages/index");
	}
}
