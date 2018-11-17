package com.example.demo.service;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService implements UserDetailsService {
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (username == null) {
      throw new UsernameNotFoundException("empty");
    }

    com.example.demo.domain.User user = userService.find(username);
    String password = passwordEncoder.encode(user.getPassword());

    return new User(username, password, Collections.emptySet());
  }
}
