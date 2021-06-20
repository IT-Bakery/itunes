package com.rascal.itunes.services.cache;

import com.rascal.itunes.api.artist.Artist;
import com.rascal.itunes.api.favourite.Album;
import com.rascal.itunes.persistence.artist.ArtistEntity;
import com.rascal.itunes.persistence.favourite.FavouriteArtistEntity;
import java.util.List;
import java.util.Optional;

public interface FavouriteArtistCacheService {

  Optional<FavouriteArtistEntity> lookupForFavouriteInCache(String userId);

  void saveFavouriteArtist(String userId, Artist artist);

  void cacheAlbums(ArtistEntity artist, List<Album> albums);
}
