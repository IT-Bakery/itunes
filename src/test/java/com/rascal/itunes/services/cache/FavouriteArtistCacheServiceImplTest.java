package com.rascal.itunes.services.cache;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rascal.itunes.api.artist.Artist;
import com.rascal.itunes.api.favourite.Album;
import com.rascal.itunes.persistence.album.AlbumRepository;
import com.rascal.itunes.persistence.artist.ArtistEntity;
import com.rascal.itunes.persistence.artist.ArtistsRepository;
import com.rascal.itunes.persistence.favourite.FavouriteArtistEntity;
import com.rascal.itunes.persistence.favourite.FavouriteArtistRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FavouriteArtistCacheServiceImplTest {

  private final static ObjectMapper mapper = new ObjectMapper();
  private final static String USER_ID = "userId";
  private final static String ARTIST_NAME = "artistName";
  private final static Integer AMG_ARTIST_ID = 2;

  @Mock
  private FavouriteArtistRepository favouriteArtistRepository;
  @Mock
  private ArtistsRepository artistsRepository;
  @Mock
  private AlbumRepository albumRepository;
  @Mock
  private FavouriteArtistEntity favouriteArtistEntity;

  @InjectMocks
  private FavouriteArtistCacheService cacheService = new FavouriteArtistCacheServiceImpl();

  @Test
  void shouldGetUserById() {
    when(this.favouriteArtistRepository.findById(USER_ID))
        .thenReturn(Optional.of(this.favouriteArtistEntity));

    var user = this.cacheService.lookupForFavouriteInCache(USER_ID);

    assertThat(user.isPresent());
    verify(this.favouriteArtistRepository).findById(USER_ID);
  }

  @Test
  void shouldSaveFavouriteArtist() throws JsonProcessingException {
    var artist = Artist.of(mapper.createObjectNode()
        .put("artistId", 1)
        .put("artistName", "artistName")
        .put("artistLinkUrl", "artistLinkUrl")
        .put("amgArtistId", AMG_ARTIST_ID));

    InOrder inOrder = Mockito.inOrder(this.artistsRepository, this.favouriteArtistRepository);

    this.cacheService.saveFavouriteArtist(USER_ID, artist);

    inOrder.verify(this.artistsRepository).save(
        argThat(entity -> entity.getAmgArtistId() == AMG_ARTIST_ID)
    );
    inOrder.verify(this.favouriteArtistRepository).save(
        argThat(entity -> entity.getArtist().getAmgArtistId() == artist.getAmgArtistId())
    );
  }

  @Test
  void shouldSaveTopAlbums() throws JsonProcessingException {
    var artist = Artist.of(mapper.createObjectNode()
        .put("artistId", 1)
        .put("artistName", "artistName")
        .put("artistLinkUrl", "artistLinkUrl")
        .put("amgArtistId", AMG_ARTIST_ID));

    var artistEntity = ArtistEntity.of(artist);

    var album = Album.of(mapper.createObjectNode()
        .put("artistName", ARTIST_NAME));

    var albums = List.of(album, album);

    InOrder inOrder = Mockito.inOrder(this.albumRepository, this.artistsRepository);

    this.cacheService.cacheAlbums(artistEntity, albums);

    inOrder.verify(this.albumRepository).saveAll(any(List.class));
    inOrder.verify(this.artistsRepository).save(
        argThat(entity -> entity.getAlbums().get(0).getArtistName().equals(ARTIST_NAME))
    );
  }
}