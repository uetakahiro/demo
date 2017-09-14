package com.example.domain;

import lombok.Data;

@Data
public class Movie {
	private Integer id;
	private String name;
	private String[] tags;
	private String type;
	private Integer viewNum;
}
