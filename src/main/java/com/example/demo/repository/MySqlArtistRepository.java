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

import com.example.demo.domain.Artist;

@Repository
@Primary
public class MySqlArtistRepository implements ArtistRepository {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	private static String tablename = "artist";

	private static final RowMapper<Artist> artistRowMapper = (rs, i) -> {
		Integer id = rs.getInt("id");
		String name = rs.getString("name");
		String tag = rs.getString("tag");
		Integer view_count = rs.getInt("view_count");
		String grouping = rs.getString("grouping");
		return new Artist(id, name, tag, view_count, grouping);
	};

	@Override
	public Artist findOne(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		String sql = "SELECT * FROM " + tablename + "WHERE id=:id";
		return jdbcTemplate.queryForObject(sql, param, artistRowMapper);
	}

	@Override
	public List<Artist> findAll() {
		String sql = "SELECT * FROM " + tablename;
		return jdbcTemplate.query(sql, artistRowMapper);
	}

	@Override
	public List<Artist> findAll(String sortKey, String order, String limit) {
		String sql = "SELECT * FROM " + tablename + " ORDER BY " + sortKey + " " + order + " LIMIT " + limit;
		return jdbcTemplate.query(sql, artistRowMapper);
	}

	@Override
	public Artist save(Artist artist) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(artist);
		if (artist.getId() == null) {
			jdbcTemplate.update("INSERT INTO " + tablename
					+ "(name, tag, view_count, grouping) values(:name, :tag, :view_count, :grouping)", param);
		} else {
			jdbcTemplate.update("UPDATE " + tablename
					+ " SET name = :name, tag = :tag, view_count = :view_count, grouping = :grouping WHERE id = :id",
					param);
		}

		return artist;
	}

	@Override
	public void delete(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		String sql = "DELETE FROM " + tablename + "WHERE id=:id";
		jdbcTemplate.update(sql, param);
	}

}
