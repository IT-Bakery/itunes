package com.rascal.itunes.api.artist;

import com.rascal.itunes.services.itunes.ITunesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ArtistsController {

  @Autowired
  private RestTemplate restTemplate;

  /**
   * GET artists/{term} endpoint search for artists by lookup phrase
   *
   * @param term - phrase for lookup
   * @return List of artists found by lookup phrase
   */
  @GetMapping("artists/{term}")
  ResponseEntity lookupForArtistsByName(@PathVariable String term) {
    var artists = new Artists(new ITunesServiceImpl(this.restTemplate));
    var body = artists.lookupArtists(term);

    return ResponseEntity.ok(body);
  }
}