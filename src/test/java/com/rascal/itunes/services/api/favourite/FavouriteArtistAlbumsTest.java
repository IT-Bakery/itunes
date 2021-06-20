package com.rascal.itunes.services.api.favourite;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rascal.itunes.api.favourite.FavouriteArtistAlbums;
import com.rascal.itunes.exceptions.NotFoundException;
import com.rascal.itunes.persistence.artist.ArtistEntity;
import com.rascal.itunes.persistence.favourite.FavouriteArtistEntity;
import com.rascal.itunes.services.cache.FavouriteArtistCacheService;
import com.rascal.itunes.services.itunes.ITunesService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FavouriteArtistAlbumsTest {

  private final static ObjectMapper mapper = new ObjectMapper();
  private final static String USER_ID = "userId";
  @Mock
  private FavouriteArtistCacheService cacheService;
  @Mock
  private ITunesService iTunesService;
  @Mock
  private FavouriteArtistEntity favouriteArtistEntity;
  @Mock
  private ArtistEntity artistEntity;

  private FavouriteArtistAlbums artistAlbums;

  @BeforeEach
  void setUp() {
    this.artistAlbums = new FavouriteArtistAlbums(this.cacheService, this.iTunesService);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenAmgArtistIdIsNull() {
    when(this.cacheService.lookupForFavouriteInCache(USER_ID))
        .thenReturn(Optional.of(this.favouriteArtistEntity));
    when(this.favouriteArtistEntity.getArtist()).thenReturn(this.artistEntity);
    when(this.artistEntity.getAmgArtistId()).thenReturn(null);
    assertThrows(NotFoundException.class, () -> this.artistAlbums.getTopAlbums(USER_ID));
  }

  @Test
  void shouldThrowNotFoundExceptionWhenUserDoesNotHaveFavouriteArtist() {
    when(this.cacheService.lookupForFavouriteInCache(USER_ID)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> this.artistAlbums.getTopAlbums(USER_ID));
  }
}
