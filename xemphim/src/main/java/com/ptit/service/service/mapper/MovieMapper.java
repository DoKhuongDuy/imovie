package com.ptit.service.service.mapper;

import com.ptit.service.model.entity.Episode;
import com.ptit.service.model.entity.Movie;
import com.ptit.service.model.response.MovieResponse;

import java.util.List;

public class MovieMapper {

    public static MovieResponse mapMovie2Response(Movie movie, List<Episode> episodes) {
        return MovieResponse.builder()
                .id(movie.getId())
                .name(movie.getName())
                .subName(movie.getSubName())
                .listEpisode(episodes)
                .numEpisode(movie.getNumEpisode())
                .thumbnailUrl(movie.getThumbnailUrl())
                .director(movie.getDirector())
                .duration(movie.getDuration())
                .publishDate(movie.getPublishDate())
                .updateDate(movie.getUpdateDate())
                .description(movie.getDescription())
                .type(movie.getType())
                .view(movie.getView())
                .build();
    }
}
