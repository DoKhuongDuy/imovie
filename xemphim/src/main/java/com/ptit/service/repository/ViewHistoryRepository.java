package com.ptit.service.repository;

import com.ptit.service.model.entity.ViewHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewHistoryRepository extends MongoRepository<ViewHistory, String> {
    ViewHistory findFirstByUserIdAndMovieIdAndEpisodeIdOrderByCreatedAtDesc(String userId, String movieId, String episodeId);

    List<ViewHistory> findFirstByUserIdAndMovieIdOrderByCreatedAtDesc(String userId, String movieId);

    List<ViewHistory> findAllByUserIdAndViewStatusIs(String userId, int viewStatus);
}
