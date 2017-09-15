package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movie {
	private Integer id;
	private String name;
	//private String[] tags;
	private String type;
	private Integer viewNum;
}
