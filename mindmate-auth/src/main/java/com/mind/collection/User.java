package com.mind.collection;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    @Id
    private String id;

    private String name;
    private String email;
    private String password;

    private String emergencyContact;

    private LocalDateTime createdAt;
}