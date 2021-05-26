package com.ptit.service.model.entity;

import com.ptit.service.model.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "view_histories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewHistory extends BaseEntity {
    @Id
    private String id;
    private String userId;
    private String movieId;
    private String episodeId;
    private int viewStatus;
    private long currentViewing;
}
