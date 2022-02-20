package com.example.simplenoticeboardwebflux.dto;

import com.example.simplenoticeboardwebflux.dto.User;
import com.example.simplenoticeboardwebflux.entity.NoticeBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class NoticeBoardDetailDTO {
    private NoticeBoard noticeBoard;
    private User user;
}
