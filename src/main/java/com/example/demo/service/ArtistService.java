package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Artist;
import com.example.demo.domain.Movie;
import com.example.demo.repository.ArtistRepository;

@Service
public class ArtistService {

	private Integer artistNum;
	private List<Artist> artistCache = new ArrayList<>();

	@Autowired
	ArtistRepository artistRepository;

	@PostConstruct
	public void init() {
		artistCache = artistRepository.findAll();
		artistNum = artistCache.size();
	}

	public Integer getArtistNum() {
		return artistNum;
	}

	public void refreshCache() {
		artistCache.clear();
		artistCache = artistRepository.findAll();
	}

	public List<Artist> findAll() {
		return artistCache;
	}

	public List<Artist> findAll(String grouping) {
		List<Artist> groupArtists = new ArrayList<>();
		for (Artist artist : artistCache) {
			if (grouping.equals("all") || artist.getGrouping().equals(grouping))
				groupArtists.add(artist);
		}
		return groupArtists;
	}

	public Artist findOne(Integer id) {
		Artist artist = null;
		for (Artist tmp : artistCache) {
			if (tmp.getId() == id)
				artist = tmp;
		}
		return artist;
	}

	public Artist findOne(String name) {
		for (Artist artist : artistCache) {
			if (artist.getName().equals(name))
				return artist;
		}
		return null;
	}

	public List<Artist> findTopArtist(Integer limit) {
		List<Artist> topArtists = new ArrayList<>();
		Collections.sort(artistCache, new ArtistComparator());

		for (int i = 0; i < limit; i++) {
			if (i < artistCache.size()) {
				topArtists.add(artistCache.get(i));
			}
		}

		return topArtists;
	}

	public List<Artist> findBottomArtist(Integer limit) {
		List<Artist> bottomArtists = new ArrayList<>();
		Collections.sort(artistCache, new ArtistComparatorDesc());

		for (int i = 0; i < limit; i++) {
			if (i < artistCache.size()) {
				bottomArtists.add(artistCache.get(i));
			}
		}

		return bottomArtists;
	}

	public Artist create(Artist artist) {
		return artistRepository.save(artist);
	}

	public Artist update(Artist artist) {
		return artistRepository.save(artist);
	}

	public void delete(Integer id) {
		artistRepository.delete(id);
	}

	public static class ArtistComparator implements Comparator<Artist> {

		@Override
		public int compare(Artist p1, Artist p2) {
			if (p1.getView_count() == p2.getView_count())
				return 0;
			else if (p1.getView_count() > p2.getView_count())
				return -1;
			else
				return 1;
		}
	}

	public static class ArtistComparatorDesc implements Comparator<Artist> {

		@Override
		public int compare(Artist p1, Artist p2) {
			if (p1.getView_count() == p2.getView_count())
				return 0;
			else if (p1.getView_count() > p2.getView_count())
				return 1;
			else
				return -1;
		}
	}

	public void createArtistFromMovies(List<Movie> movies) {
		List<String> artists = new ArrayList<>();
		List<Integer> artistViews = new ArrayList<>();
		List<String> artistGroups = new ArrayList<>();

		for (Movie movie : movies) {
			if (!artists.contains(movie.getArtist())) {
				artists.add(movie.getArtist());
			}
		}

		for (int i = 0; i < artists.size(); i++) {
			artistViews.add(0);
			artistGroups.add("");
		}

		for (Movie movie : movies) {
			int index = artists.indexOf(movie.getArtist());
			artistViews.set(index, artistViews.get(index) + movie.getViewNum());
			artistGroups.set(index, movie.getType());
		}

		for (int i = 0; i < artists.size(); i++) {
			boolean isExist = false;
			for (Artist artist : artistCache) {
				if (artists.get(i).equals(artist.getName())) {
					artist.setView_count(artistViews.get(i));
					update(artist);
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				Artist artist = new Artist(null, artists.get(i), null, artistViews.get(i), artistGroups.get(i));
				create(artist);
			}
		}

		refreshCache();
	}

	public List<String> createArtistNames(List<Artist> artists) {
		List<String> artistNames = new ArrayList<>();

		for (int i = 0; i < artists.size(); i++) {
			artistNames.add(artists.get(i).getName());
		}

		return artistNames;
	}

	public List<Integer> createArtistViews(List<Artist> artists) {
		List<Integer> artistViews = new ArrayList<>();

		for (int i = 0; i < artists.size(); i++) {
			artistViews.add(artists.get(i).getView_count());
		}

		return artistViews;
	}
}
