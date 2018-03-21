package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Artist {
	private Integer id;
	private String name;
	private String tag;
	private Integer view_count;
	private String grouping;
}
