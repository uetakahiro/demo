package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Movie;
import com.example.demo.service.MovieService;

@RestController
@RequestMapping("movies")
public class MovieRestController {
	@Autowired
	MovieService movieService;

	@RequestMapping("/")
	public String getTest() {
		return "test";
	}

	@RequestMapping(method = RequestMethod.GET)
	List<Movie> getMovies() {
		System.out.println("List movie get is requested.");
		return movieService.findAll();
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	Movie getMovie(@PathVariable Integer id) {
		System.out.println("movie get is requested");
		return movieService.findOne(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	Movie postMovie(@RequestBody Movie movie) {
		return movieService.create(movie);
	}
}
