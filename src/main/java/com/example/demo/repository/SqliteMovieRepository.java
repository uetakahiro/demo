package com.example.demo.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.Movie;

@Repository
public class SqliteMovieRepository implements MovieRepository {

	private static String dbPath = "jdbc:sqlite:D:/Tools/WhiteBrowser/idle.wb";
	private static String tableName = "movie";
	private static final String item_list = "movie_id, movie_name, view_count, tag, movie_path, artist, grouping, hash";

	public List<Movie> findAll() {
		String sql = "SELECT " + item_list + " FROM " + tableName;
		return findAllCommon(sql);
	}

	public List<Movie> findAll(String sortKey, String order, String limit) {
		String sql = "SELECT " + item_list + " FROM " + tableName + " ORDER BY " + sortKey + " "
				+ order
				+ " LIMIT "
				+ limit;
		return findAllCommon(sql);
	}

	public List<Movie> findTopView(Integer limit) {
		String sql = "SELECT " + item_list + " FROM " + tableName + " ORDER BY view_count DESC LIMIT "
				+ limit.toString();
		return findAllCommon(sql);
	}

	public Movie findOne(Integer id) {
		String sql = "SELECT " + item_list + " FROM " + tableName + " WHERE movie_id = "
				+ id.toString();
		Movie movie = new Movie();
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			movie = new Movie(rs.getInt("movie_id"), rs.getString("movie_name"), rs.getString("tag"),
					rs.getInt("view_count"), rs.getString("movie_path"), rs.getString("artist"),
					rs.getString("grouping"), rs.getString("hash"));

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return movie;
	}

	public Movie save(Movie movie) {
		String sql;
		if (movie.getId() == null) {
			return movie;
		} else {
			sql = "UPDATE " + tableName + " SET movie_name = '" + movie.getName() + "', view_count = "
					+ movie.getViewNum().toString() + ", artist = '" + movie.getArtist() + "', grouping = '"
					+ movie.getType() + "' WHERE movie_id = "
					+ movie.getId().toString();
		}

		try {
			Connection conn = this.connect();
				Statement stmt = conn.createStatement();
			int ret = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return movie;
	}

	public void delete(Integer id) {
		String sql = "DELETE FROM " + tableName + " WHERE id = " + id.toString();
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Connect to the test.db database
	 * 
	 * @return the Connection object
	 */
	private Connection connect() {
		// SQLite connection string
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dbPath);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	private List<Movie> findAllCommon(String sql) {
		List<Movie> movies = new ArrayList<>();

		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {
				movies.add(new Movie(rs.getInt("movie_id"), rs.getString("movie_name"), rs.getString("tag"),
						rs.getInt("view_count"), rs.getString("movie_path"), rs.getString("artist"),
						rs.getString("grouping"), rs.getString("hash")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return movies;
	}
}
