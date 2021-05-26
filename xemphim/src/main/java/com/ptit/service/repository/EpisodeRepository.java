package com.ptit.service.repository;

import com.ptit.service.model.entity.Episode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeRepository extends MongoRepository<Episode, String> {
    List<Episode> findAllByMovieIdIn(List<String> listMovieIds);

    Episode getById(String episodeId);
}
