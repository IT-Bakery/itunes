package com.rascal.itunes.api.favourite;

import com.fasterxml.jackson.databind.JsonNode;
import com.rascal.itunes.services.cache.FavouriteArtistCacheService;
import com.rascal.itunes.services.itunes.ITunesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class FavouriteArtistController {

  @Autowired
  private FavouriteArtistCacheService favouriteArtistCacheService;
  @Autowired
  private RestTemplate restTemplate;

  /**
   * POST favourite/ endpoint saves user's favourite artist
   *
   * @param userId
   * @param artist
   */
  @PostMapping("favourite/")
  ResponseEntity saveFavouriteArtist(
      @RequestHeader("userId") String userId,
      @RequestBody JsonNode artist
  ) {
    var favouriteArtist = new FavouriteArtist(this.favouriteArtistCacheService);
    favouriteArtist.saveFavouriteArtist(userId, artist);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * GET favourite/top/ endpoint returns list of user's favourite artist top albums
   *
   * @param userId
   * @return
   */
  @GetMapping("favourite/top/")
  ResponseEntity getFavouriteArtistTopAlbumList(@RequestHeader("userId") String userId) {
    var body = new FavouriteArtistAlbums(
        this.favouriteArtistCacheService,
        new ITunesServiceImpl(this.restTemplate)
    ).getTopAlbums(userId);
    return ResponseEntity.ok(body);
  }
}
