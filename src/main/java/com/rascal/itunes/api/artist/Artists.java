package com.rascal.itunes.api.artist;

import com.rascal.itunes.services.itunes.ITunesService;
import java.util.List;
import java.util.Objects;

public class Artists {

  private final ITunesService iTunesService;

  public Artists(ITunesService iTunesService) {
    this.iTunesService = Objects.requireNonNull(iTunesService);
  }

  public List<Artist> lookupArtists(String term) {
    return iTunesService.lookupArtists(term);
  }
}
