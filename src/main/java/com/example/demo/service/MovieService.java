package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Movie;
import com.example.demo.repository.MovieRepository;

@Service
public class MovieService {

	private List<Movie> movieCache = new ArrayList<>();

	@Autowired
	MovieRepository movieRepository;

	@PostConstruct
	public void init() {
		movieCache = movieRepository.findAll();
	}

	public void refreshCache() {
		movieCache.clear();
		movieCache = movieRepository.findAll();
	}

	public List<Movie> findAll() {
		return movieCache;
	}

	public List<Movie> findTopView(Integer limit) {
		List<Movie> tmpMovies = new ArrayList<>();
		for(Movie movie : movieCache) {
			tmpMovies.add(movie);
		}
		Collections.sort(tmpMovies, new MovieComparator());
		List<Movie> topMovies = new ArrayList<>();
		for(int i = 0; i < limit; i++) {
			topMovies.add(tmpMovies.get(i));
		}
		return topMovies;
	}

	public List<Movie> findArtistMovies(String artist) {
		List<Movie> artistMovies = new ArrayList<>();
		for (Movie movie : movieCache) {
			if (movie.getArtist().equals(artist)) {
				artistMovies.add(movie);
			}
		}
		return artistMovies;
	}

	public Movie findOne(Integer id) {
		Movie movie = null;
		for (Movie tmpMovie : movieCache) {
			if (tmpMovie.getId() == id)
				movie = tmpMovie;
		}
		return movie;
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

	public static class MovieComparator implements Comparator<Movie> {

		@Override
		public int compare(Movie p1, Movie p2) {
			if (p1.getViewNum() == p2.getViewNum())
				return 0;
			else if (p1.getViewNum() > p2.getViewNum())
				return -1;
			else
				return 1;
		}
	}

	public List<String> createTagList(List<Movie> movies) {
		List<String> tags = new ArrayList<>();
		for (Movie movie : movies) {
			String[] tmp = movie.getTag().split(",");
			for (String tag : tmp) {
				if (tag.length() > 1 && !tags.contains(tag)) {
					tags.add(tag);
				}
			}
		}
		return tags;
	}

	public List<Integer> createTagNums(List<Movie> movies, List<String> tags, String type) {
		List<Integer> tagNums = new ArrayList<>();

		for (int i = 0; i < tags.size(); i++) {
			tagNums.add(0);
		}

		for (Movie movie : movies) {
			String[] tmp = movie.getTag().split(",");
			for (String tag : tmp) {
				if (movie.getType().equals(type) && tag.length() > 1) {
					int index = tags.indexOf(tag);
					tagNums.set(index, tagNums.get(index) + 1);
				}
			}
		}

		return tagNums;
	}

	public static class TypeComparator implements Comparator<MovieType> {

		@Override
		public int compare(MovieType p1, MovieType p2) {
			if (p1.getNum() == p2.getNum())
				return 0;
			else if (p1.getNum() > p2.getNum())
				return -1;
			else
				return 1;
		}
	}

	private static class MovieType {
		private String name;
		private Integer num;

		String getName() {
			return this.name;
		}

		void setName(String name) {
			this.name = name;
		}

		Integer getNum() {
			return this.num;
		}

		void setNum(Integer num) {
			this.num = num;
		}

		public MovieType(String name, Integer num) {
			this.name = name;
			this.num = num;
		}
	}
}
