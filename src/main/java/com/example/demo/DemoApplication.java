package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.repository.MovieRepository;

@EnableAutoConfiguration
@ComponentScan
public class DemoApplication implements CommandLineRunner {
	@Autowired
	MovieRepository movieRepository;

	@Override
	public void run(String... strings) throws Exception {
		movieRepository.findAll().forEach(System.out::println);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
