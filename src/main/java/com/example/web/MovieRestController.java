package com.example.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Movie;
import com.example.service.DemoService;

@RestController
@RequestMapping("movies")
public class MovieRestController {
	@Autowired
	DemoService demoService;

	/*
	 * get list
	 */
	@RequestMapping(method = RequestMethod.GET)
	List<Movie> getMovies() {
		return demoService.findAll();
	}
}
