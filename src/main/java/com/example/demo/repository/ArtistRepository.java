package com.example.demo.repository;

import java.util.List;

import com.example.demo.domain.Artist;

public interface ArtistRepository {
	public Artist findOne(Integer id);

	public List<Artist> findAll();

	public List<Artist> findAll(String sortKey, String order, String limit);

	public Artist save(Artist movie);

	public void delete(Integer id);
}
