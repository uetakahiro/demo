package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Movie;
import com.example.repository.MovieRepository;

@Service
public class DemoService {

	@Autowired
	MovieRepository movieRepository;

	public List<Movie> findAll() {
		return movieRepository.findAll();
	}
    
}
