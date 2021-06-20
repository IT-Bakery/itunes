package com.rascal.itunes.services.cache;

import com.rascal.itunes.api.artist.Artist;
import com.rascal.itunes.api.favourite.Album;
import com.rascal.itunes.persistence.album.AlbumEntity;
import com.rascal.itunes.persistence.album.AlbumRepository;
import com.rascal.itunes.persistence.artist.ArtistEntity;
import com.rascal.itunes.persistence.artist.ArtistsRepository;
import com.rascal.itunes.persistence.favourite.FavouriteArtistEntity;
import com.rascal.itunes.persistence.favourite.FavouriteArtistRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for caching iTunes and user data
 */
@Service
public class FavouriteArtistCacheServiceImpl implements FavouriteArtistCacheService {

  @Autowired
  private FavouriteArtistRepository favouriteArtistRepository;
  @Autowired
  private ArtistsRepository artistsRepository;
  @Autowired
  private AlbumRepository albumRepository;

  /**
   * Gets user model FavouriteArtistEntity by userId
   *
   * @param userId
   * @return user entity Optional<FavouriteArtistEntity>
   */
  @Override
  public Optional<FavouriteArtistEntity> lookupForFavouriteInCache(String userId) {
    return this.favouriteArtistRepository.findById(userId);
  }

  /**
   * Constructs artist and user entities and saves them into the cache
   *
   * @param userId
   * @param artist
   */
  @Override
  public void saveFavouriteArtist(String userId, Artist artist) {
    var artistEntity = ArtistEntity.of(artist);
    this.artistsRepository.save(artistEntity);

    var favouriteArtistEntity = new FavouriteArtistEntity(userId, artistEntity);
    this.favouriteArtistRepository.save(favouriteArtistEntity);
  }

  /**
   * Constructs album entities and saves them to cache
   * Updates artist relations
   *
   * @param artist
   * @param albums
   */
  @Override
  public void cacheAlbums(ArtistEntity artist, List<Album> albums) {
    List<AlbumEntity> albumEntities = albums.stream()
        .map(AlbumEntity::of)
        .map(album -> album.withArtist(artist))
        .collect(Collectors.toList());

    this.albumRepository.saveAll(albumEntities);

    artist.setAlbums(albumEntities);
    this.artistsRepository.save(artist);
  }
}
