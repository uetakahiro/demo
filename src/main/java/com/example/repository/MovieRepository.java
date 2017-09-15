package com.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import com.example.domain.Movie;

@Repository
public class MovieRepository {
	private final ConcurrentMap<Integer, Movie> movieMap = new ConcurrentHashMap<>();

	public List<Movie> findAll() {
		return new ArrayList<>(movieMap.values());
	}

	public Movie findOne(Integer id) {
		return movieMap.get(id);
	}

	public Movie save(Movie movie) {
		return movieMap.put(movie.getId(), movie);
	}

	public void delete(Integer id) {
		movieMap.remove(id);
	}
}
