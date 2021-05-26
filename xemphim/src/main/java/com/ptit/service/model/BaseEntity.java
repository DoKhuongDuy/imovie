package com.ptit.service.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
public class BaseEntity {
    @CreatedDate
    private long createdAt;

    @LastModifiedDate
    private long updatedAt;
}
