package com.example.simplenoticeboardwebflux.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@Getter
@Table("tb_notice_board")
public class NoticeBoard {

    @Id
    private final Long id;

    private final String title;

    private final String contents;

    private final String author;
}
