package com.ptit.service.model.entity;

import com.ptit.service.model.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "book_marks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookMark extends BaseEntity {
    @Id
    private String id;
    private String userId;
    private String movieId;
}
