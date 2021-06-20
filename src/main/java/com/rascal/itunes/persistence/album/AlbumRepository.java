package com.rascal.itunes.persistence.album;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends CrudRepository<AlbumEntity, Integer> {

}
