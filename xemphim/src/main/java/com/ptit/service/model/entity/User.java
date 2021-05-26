package com.ptit.service.model.entity;

import com.ptit.service.model.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
}
