package com.example.simplenoticeboardwebflux;

import com.example.simplenoticeboardwebflux.entity.NoticeBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RequestMapping("/notice/boards")
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public Flux<NoticeBoard> getBoards() {
        return boardService.getBoards();
    }


}
