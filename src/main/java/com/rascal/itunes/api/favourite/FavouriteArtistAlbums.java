package com.rascal.itunes.api.favourite;

import com.rascal.itunes.exceptions.NotFoundException;
import com.rascal.itunes.persistence.album.AlbumEntity;
import com.rascal.itunes.persistence.artist.ArtistEntity;
import com.rascal.itunes.persistence.favourite.FavouriteArtistEntity;
import com.rascal.itunes.services.cache.FavouriteArtistCacheService;
import com.rascal.itunes.services.itunes.ITunesService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FavouriteArtistAlbums {

  private final FavouriteArtistCacheService cacheService;
  private final ITunesService iTunesService;

  public FavouriteArtistAlbums(
      FavouriteArtistCacheService cacheService,
      ITunesService iTunesService
  ) {
    this.cacheService = Objects.requireNonNull(cacheService);
    this.iTunesService = Objects.requireNonNull(iTunesService);
  }

  public List<Album> getTopAlbums(String userId) {
    var favourite = this.cacheService.lookupForFavouriteInCache(userId);
    validateRelationToAlbums(favourite);
    ArtistEntity artist = favourite.get().getArtist();
    return getAlbums(artist);
  }

  private List<Album> getAlbums(ArtistEntity artist) {
    var albums = artist.getAlbums().stream()
        .filter(isRecordExpired())
        .map(Album::of)
        .collect(Collectors.toList());
    if (albums.isEmpty()) {
      albums = this.iTunesService.getTopAlbums(artist.getAmgArtistId());
      this.cacheService.cacheAlbums(artist, albums);
    }
    return albums;
  }

  private Predicate<AlbumEntity> isRecordExpired() {
    return album -> album.getCreated()
        .after(Timestamp.valueOf(LocalDateTime.now().minusHours(1)));
  }

  private void validateRelationToAlbums(Optional<FavouriteArtistEntity> favourite) {
    if (
        favourite.isEmpty() || favourite.get().getArtist().getAmgArtistId() == null
    ) {
      throw new NotFoundException(new Exception());
    }
  }
}
