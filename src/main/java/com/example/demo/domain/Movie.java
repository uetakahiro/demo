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
@Table(name = "metadata")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(nullable = false)
	private String name;
	//private String[] tags;
	@Column(nullable = false)
	private String type;
	@Column(nullable = false)
	private Integer viewNum;
}
