package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Movie;

@Repository
public class MovieRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	private static String dbname = "metadata";

	private static final RowMapper<Movie> movieRowMapper = (rs, i) -> {
		Integer id = rs.getInt("id");
		String name = rs.getString("name");
		String type = rs.getString("type");
		Integer viewNum = rs.getInt("viewNum");
		return new Movie(id, name, type, viewNum);
	};

	public List<Movie> findAll() {
		List<Movie> movies = jdbcTemplate.query("SELECT * FROM " + dbname, movieRowMapper);
		return movies;
	}

	public Movie findOne(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return jdbcTemplate.queryForObject("SELECT * FROM " + dbname + "WHERE id=:id", param, movieRowMapper);
	}

	public Movie save(Movie movie) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(movie);
		if (movie.getId() == null) {
			jdbcTemplate.update("INSERT INTO " + dbname + "(name, type, viewNum) values(:name, :type, :viewNum", param);
		} else {
			jdbcTemplate.update("UPDATE " + dbname + "SET name=:name, type:type, viewNum:viewNum WHERE id=:id", param);
		}

		return movie;
	}

	public void delete(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		jdbcTemplate.update("DELETE FROM " + dbname + "WHERE id=:id", param);
	}
}
