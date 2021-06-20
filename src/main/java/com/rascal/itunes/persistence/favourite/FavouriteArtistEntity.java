package com.rascal.itunes.persistence.favourite;

import com.rascal.itunes.persistence.artist.ArtistEntity;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "favourite")
public class FavouriteArtistEntity {

  public FavouriteArtistEntity() {
  }

  @Id
  private String userId;
  @ManyToOne
  private ArtistEntity artist;

  public FavouriteArtistEntity(String userId, ArtistEntity artist) {
    this.userId = Objects.requireNonNull(userId);
    this.artist = Objects.requireNonNull(artist);
  }

  public ArtistEntity getArtist() {
    return artist;
  }
}
