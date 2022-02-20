package com.example.simplenoticeboardwebflux;

import com.example.simplenoticeboardwebflux.dto.NoticeBoardDetailDTO;
import com.example.simplenoticeboardwebflux.entity.NoticeBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/notice/boards")
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public Flux<NoticeBoard> getBoards() {
        return boardService.getBoards();
    }

    @GetMapping("/{boardId}")
    public Mono<NoticeBoardDetailDTO> getBoard(@PathVariable Long boardId) {
        return boardService.getBoard(boardId);
    }

    @PostMapping
    public Mono<Long> registerBoard(@RequestBody NoticeBoard noticeBoard) {
        return boardService.registerBoard(noticeBoard).map(NoticeBoard::getId);
    }

    @PutMapping
    public Mono<Long> modifyBoard(@RequestBody NoticeBoard noticeBoard) {
        return boardService.modifyBoard(noticeBoard);
    }

    @DeleteMapping("/{boardId}")
    public Mono<Long> deleteBoard(@PathVariable("boardId") long boardId) {
        return boardService.deleteBoard(boardId);
    }
}
