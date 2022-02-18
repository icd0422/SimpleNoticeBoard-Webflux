package com.example.simplenoticeboardwebflux;

import com.example.simplenoticeboardwebflux.entity.NoticeBoard;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class BoardService {

    private final ReactiveBoardRepository reactiveBoardRepository;

    public Flux<NoticeBoard> getBoards() {
        return reactiveBoardRepository.findAll();
    }

    public Mono<NoticeBoard> getBoard(Long boardId) {
        return reactiveBoardRepository.findById(boardId);
    }

    public Mono<NoticeBoard> registerBoard(NoticeBoard noticeBoard) {
        return reactiveBoardRepository.save(noticeBoard);
    }

    public Mono<Long> modifyBoard(NoticeBoard noticeBoard) {
        return reactiveBoardRepository.findById(noticeBoard.getId())
                .switchIfEmpty(Mono.defer(() -> {
                    throw new RuntimeException("id : " + noticeBoard.getId() + "인 게시판을 찾을 수 없습니다.");
                })).flatMap(it -> {
                            it.setTitle(noticeBoard.getTitle());
                            it.setContents(noticeBoard.getContents());
                            it.setAuthor(noticeBoard.getAuthor());
                            it.setUpdatedAt(LocalDateTime.now());

                            return reactiveBoardRepository.save(it);
                        }
                ).map(NoticeBoard::getId);
    }

    public Mono<Long> deleteBoard(long boardId) {
        return reactiveBoardRepository.removeById(boardId);
    }
}
