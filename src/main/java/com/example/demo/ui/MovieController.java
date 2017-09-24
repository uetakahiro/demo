package com.example.demo.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.domain.Movie;
import com.example.demo.service.MovieService;

@Controller
public class MovieController {
	@Autowired
	MovieService movieService;

	@RequestMapping(method = RequestMethod.GET)
	String list(Model model) {
		List<Movie> movies = movieService.findAll();
		model.addAttribute("movies", movies);
		return "movies/list";
	}

}
