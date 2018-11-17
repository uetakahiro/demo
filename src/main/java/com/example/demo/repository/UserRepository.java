package com.example.demo.repository;

import com.example.demo.domain.User;

public interface UserRepository {
  public User find(String name);
}
