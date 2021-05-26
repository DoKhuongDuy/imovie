package com.ptit.service.model.entity;

import com.ptit.service.model.BaseEntity;
import com.ptit.service.model.constant.MovieType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie extends BaseEntity {
    @Id
    private String id;
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
