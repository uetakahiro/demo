package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Movie;

@Repository
@Primary
public class MySqlMovieRepository implements MovieRepository {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	private static String tablename = "movie";

	private static final RowMapper<Movie> movieRowMapper = (rs, i) -> {
		Integer id = rs.getInt("movie_id");
		String name = rs.getString("movie_name");
		String tag = rs.getString("tag");
		Integer viewNum = rs.getInt("view_count");
		String path = rs.getString("movie_path");
		String artist = rs.getString("artist");
		String type = rs.getString("grouping");
		String hash = rs.getString("hash");
		return new Movie(id, name, tag, viewNum, path, artist, type, hash);
	};

	public List<Movie> findAll() {
		String sql = "SELECT * FROM " + tablename;
		return jdbcTemplate.query(sql, movieRowMapper);
	}

	public List<Movie> findAll(String sortKey, String order, String limit) {
		String sql = "SELECT * FROM " + tablename + " ORDER BY " + sortKey + " " + order + " LIMIT " + limit;
		return jdbcTemplate.query(sql, movieRowMapper);
	}

	public List<Movie> findTopView(Integer limit) {
		String sql = "SELECT * FROM " + tablename + " ORDER BY view_count DESC LIMIT " + limit.toString();
		return jdbcTemplate.query(sql, movieRowMapper);
	}

	public Movie findOne(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("movie_id", id);
		String sql = "SELECT * FROM " + tablename + "WHERE movie_id=:id";
		return jdbcTemplate.queryForObject(sql, param, movieRowMapper);
	}

	public Movie save(Movie movie) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(movie);
		if (movie.getId() == null) {
			jdbcTemplate.update("INSERT INTO " + tablename
					+ "(movie_name, tag, view_count, movie_path, artist, grouping, hash) values(:name, :tag, :viewNum, :path, artist, type, hash)",
					param);
		} else {
			jdbcTemplate.update("UPDATE " + tablename
					+ " SET movie_name = :name, tag = :tag, view_count = :viewNum, movie_path = :path, artist = :artist, grouping = :type, hash = :hash WHERE movie_id = :id",
					param);
		}

		return movie;
	}

	public void delete(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("movie_id", id);
		String sql = "DELETE FROM " + tablename + "WHERE movie_id=:id";
		jdbcTemplate.update(sql, param);
	}

}
