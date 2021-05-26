package com.ptit.service.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ptit.service.model.constant.MovieType;
import com.ptit.service.model.entity.Episode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieResponse {
    private String id;
    private List<Episode> listEpisode;
    private String name;
    private String subName;
    private String thumbnailUrl;
    private String duration; // minutes
    private String director;
    private String numEpisode;
    private String publishDate;
    private String updateDate;
    private String description;
    private List<MovieType> type;
    private int view;
}
