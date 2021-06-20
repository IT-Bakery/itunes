package com.rascal.itunes.api.favourite;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.rascal.itunes.api.artist.Artist;
import com.rascal.itunes.exceptions.BadRequestException;
import com.rascal.itunes.exceptions.ForbiddenException;
import com.rascal.itunes.services.cache.FavouriteArtistCacheService;
import java.util.Objects;

public class FavouriteArtist {

  private final FavouriteArtistCacheService cacheService;

  public FavouriteArtist(FavouriteArtistCacheService cacheService) {
    this.cacheService = Objects.requireNonNull(cacheService);
  }

  public void saveFavouriteArtist(String userId, JsonNode requestBody) {
    validateUserDoesNotHaveFavouriteArtist(userId);
    var artist = deserializeArtist(requestBody);
    this.cacheService.saveFavouriteArtist(userId, artist);
  }

  private Artist deserializeArtist(JsonNode requestBody) {
    try {
      return Artist.of(requestBody);
    } catch (JsonProcessingException e) {
      throw new BadRequestException(e);
    }
  }

  private void validateUserDoesNotHaveFavouriteArtist(String userId) {
    this.cacheService.lookupForFavouriteInCache(userId).ifPresent(
        favourite -> {
          throw new ForbiddenException(new Exception());
        });
  }
}
