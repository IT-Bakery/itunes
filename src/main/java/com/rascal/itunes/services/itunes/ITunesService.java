package com.rascal.itunes.services.itunes;

import com.rascal.itunes.api.artist.Artist;
import com.rascal.itunes.api.favourite.Album;
import java.util.List;

public interface ITunesService {

  List<Artist> lookupArtists(String term);

  List<Album> getTopAlbums(Integer amgArtistId);
}
