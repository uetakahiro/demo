package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import com.example.demo.domain.User;

@Repository
public class MySqlUserRepository implements UserRepository {

  private String tablename = "user";

  @Autowired
  NamedParameterJdbcTemplate jdbcTemplate;

  private static final RowMapper<User> userRowMapper = (rs, i) -> {
    String id = rs.getString("id");
    String name = rs.getString("name");
    String password = rs.getString("password");
    return new User(id, name, password);
  };

  @Override
  public User find(String name) {
    SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
    String sql = "SELECT * FROM " + tablename + " WHERE name=:name";
    return jdbcTemplate.queryForObject(sql, param, userRowMapper);
  }
}
