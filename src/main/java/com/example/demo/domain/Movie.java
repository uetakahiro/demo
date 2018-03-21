package com.example.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String tag;
	@Column(nullable = false)
	private Integer viewNum;
	@Column(nullable = false)
	private String path;
	@Column(nullable = true)
	private String artist;
	@Column(nullable = true)
	private String type;
	@Column(nullable = true)
	private String hash;
}
