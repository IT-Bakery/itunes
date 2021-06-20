package com.rascal.itunes.services.api.artist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rascal.itunes.api.artist.Artist;
import com.rascal.itunes.api.artist.Artists;
import com.rascal.itunes.services.itunes.ITunesService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArtistsTest {
  private final static String TERM = "term";

  @Mock
  private ITunesService iTunesService;
  @Mock
  private Artist artist;

  private Artists artists;

  @BeforeEach
  void setUp() {
    this.artists = new Artists(this.iTunesService);
  }

  @Test
  void shouldReturnListOfArtists() {
    when(this.iTunesService.lookupArtists(TERM)).thenReturn(List.of(this.artist));
    var result = this.artists.lookupArtists(TERM);

    assertEquals(artist, result.get(0));
    verify(this.iTunesService).lookupArtists(TERM);
  }
}
