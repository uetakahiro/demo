package com.example.demo.repository;

import java.util.List;

import com.example.demo.domain.Movie;

public interface MovieRepository {
	public Movie findOne(Integer id);

	public List<Movie> findAll();

	public List<Movie> findAll(String sortKey, String order, String limit);

	public List<Movie> findTopView(Integer limit);

	public Movie save(Movie movie);

	public void delete(Integer id);
}
