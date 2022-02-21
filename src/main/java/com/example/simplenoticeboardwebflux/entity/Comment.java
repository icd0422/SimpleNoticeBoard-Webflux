package com.example.simplenoticeboardwebflux.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Setter
@Getter
@Table("tb_notice_comment")
public class Comment {
    @Id
    private Long id;
    private Long boardId;
    private Long uid;
    private String content;
    private Character gender;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
