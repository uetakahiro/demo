package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Movie;
import com.example.repository.MovieRepository;

@Service
public class MovieService {

	@Autowired
	MovieRepository movieRepository;

	public List<Movie> findAll() {
		return movieRepository.findAll();
	}

	public Movie findOne(Integer id) {
		return movieRepository.findOne(id);
	}

	public Movie create(Movie movie) {
		return movieRepository.save(movie);
	}

	public Movie update(Movie movie) {
		return movieRepository.save(movie);
	}

	public void delete(Integer id) {
		movieRepository.delete(id);
	}
}
