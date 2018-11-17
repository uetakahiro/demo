package com.example.demo.ui;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.demo.config.AwsConfiguration;
import com.example.demo.domain.Artist;
import com.example.demo.domain.Movie;
import com.example.demo.service.ArtistService;
import com.example.demo.service.MovieService;

@Controller
public class MovieController {
  @Autowired
  MovieService movieService;

  @Autowired
  ArtistService artistService;

  @Autowired
  AwsConfiguration awsConfig;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  String index(Model model) {
    return "redirect:/cover";
  }

  @RequestMapping(value = "cover", method = RequestMethod.GET)
  String cover(Model model) {

    String imgPath =
        "https://" + awsConfig.getCloudFrontDomain() + "/" + awsConfig.getS3StaticFilePath();
    model.addAttribute("imgPath", imgPath);

    return "movies/cover";
  }

  @RequestMapping(value = "cover", method = RequestMethod.POST)
  public String coverlogin() {
    return "movies/cover";
  }

  @RequestMapping(value = "movies", method = RequestMethod.GET)
  String list(Model model) {
    String imgPath =
        "https://" + awsConfig.getCloudFrontDomain() + "/" + awsConfig.getS3StaticFilePath();
    model.addAttribute("imgPath", imgPath);

    model.addAttribute("awsConfig", awsConfig);

    List<Movie> movies = movieService.findAll();
    model.addAttribute("movies", movies);

    List<Movie> topMovies = movieService.findTopView(20);
    model.addAttribute("topMovies", topMovies);

    List<Movie> bottomMovies = new ArrayList<>();
    for (Movie movie : movies) {
      if (movie.getViewNum() <= 1) {
        bottomMovies.add(movie);
      }
    }
    model.addAttribute("bottomMovies", bottomMovies);

    // type
    String[] types = new String[] {"idle", "other"};
    Integer[] typeNum = new Integer[] {0, 0};
    for (Movie movie : movies) {
      if (movie.getPath().contains("Other")) {
        typeNum[1]++;
      } else {
        typeNum[0]++;
      }
    }
    model.addAttribute("types", types);
    model.addAttribute("typeNum", typeNum);

    // tag
    List<String> tags = movieService.createTagList(movies);
    model.addAttribute("tags", tags);
    model.addAttribute("idleTagNums", movieService.createTagNums(movies, tags, "idle"));
    model.addAttribute("otherTagNums", movieService.createTagNums(movies, tags, "other"));

    // artist
    List<Artist> topArtists = artistService.findTopArtist(20);
    List<Artist> bottomArtists = artistService.findBottomArtist(20);
    model.addAttribute("topArtists", topArtists);
    model.addAttribute("topArtistNames", artistService.createArtistNames(topArtists));
    model.addAttribute("topArtistViews", artistService.createArtistViews(topArtists));
    model.addAttribute("bottomArtists", bottomArtists);
    model.addAttribute("bottomArtistNames", artistService.createArtistNames(bottomArtists));
    model.addAttribute("bottomArtistViews", artistService.createArtistViews(bottomArtists));

    return "movies/list";
  }

  @RequestMapping(value = "artists/asc", method = RequestMethod.GET)
  String topArtists(Model model) {
    String imgPath =
        "https://" + awsConfig.getCloudFrontDomain() + "/" + awsConfig.getS3StaticFilePath();
    model.addAttribute("imgPath", imgPath);
    // type
    String[] types = new String[] {"idle", "other"};
    Integer[] typeNum =
        new Integer[] {artistService.findAll("idle").size(), artistService.findAll("other").size()};
    model.addAttribute("types", types);
    model.addAttribute("typeNum", typeNum);
    model.addAttribute("artists", artistService.findTopArtist(artistService.getArtistNum()));
    return "movies/artists";
  }

  @RequestMapping(value = "artists/desc", method = RequestMethod.GET)
  String bottomArtists(Model model) {
    String imgPath =
        "https://" + awsConfig.getCloudFrontDomain() + "/" + awsConfig.getS3StaticFilePath();
    model.addAttribute("imgPath", imgPath);
    model.addAttribute("artists", artistService.findBottomArtist(artistService.getArtistNum()));
    return "movies/artists";
  }

  @RequestMapping(value = "artists/{artist}", method = RequestMethod.GET)
  String artistView(@PathVariable("artist") String artist, Model model) {
    String imgPath =
        "https://" + awsConfig.getCloudFrontDomain() + "/" + awsConfig.getS3StaticFilePath();
    model.addAttribute("imgPath", imgPath);
    model.addAttribute("artistMovies", movieService.findArtistMovies(artist));
    model.addAttribute("artistName", artist);
    String artistDmmUrl;
    if (artistService.findOne(artist).getGrouping().equals("idle")) {
      artistDmmUrl = "http://www.dmm.com/search/=/searchstr=" + artist + "/limit=30/"
          + "n1=FgRCTw9VBA4GF1RWR1cK/" + "n2=Aw1fVhQKX0BdC0VZX2kCQQU_/"
          + "n3=AgReSwMKX1tcCl0ClpTDk8Ssk90_/" + "sort=rankprofile/";
    } else {
      artistDmmUrl = "http://www.dmm.co.jp/search/=/searchstr=" + artist + "/analyze=V1EBAVYFUQY_/"
          + "n1=FgRCTw9VBA4GF1RWR1cK/" + "n2=Aw1fVhQKX0BdC0VZX2kCQQU_/" + "sort=ranking/";
    }
    model.addAttribute("artistDmmUrl", artistDmmUrl);
    return "movies/artist";
  }

  @RequestMapping(value = "movies/artists", method = RequestMethod.POST)
  String createArtists() {
    movieService.createArtist();
    return "redirect:/movies";
  }

  @RequestMapping(value = "movies/types", method = RequestMethod.POST)
  String createTypes() {
    movieService.createTypes();
    return "redirect:/movies";
  }

  @RequestMapping(value = "movies/caps-moviename", method = RequestMethod.POST)
  String createCapsMovieName() {
    movieService.capsMovie();
    return "redirect:/movies";
  }

  @RequestMapping(value = "movies/refresh-cache", method = RequestMethod.POST)
  String refreshCache() {
    movieService.refreshCache();
    return "redirect:/movies";
  }

  @RequestMapping(value = "movies/artist-table", method = RequestMethod.POST)
  String updateArtistTable() {
    artistService.createArtistFromMovies(movieService.findAll());
    return "redirect:/movies";
  }

  @RequestMapping(value = "movies/migrate", method = RequestMethod.POST)
  String migrate() {
    movieService.capsMovie();
    movieService.createTypes();
    movieService.createArtist();
    artistService.createArtistFromMovies(movieService.findAll());
    movieService.refreshCache();

    return "redirect:/movies";
  }
}
