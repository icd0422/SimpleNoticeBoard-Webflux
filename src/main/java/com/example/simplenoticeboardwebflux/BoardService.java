package com.example.simplenoticeboardwebflux;

import com.example.simplenoticeboardwebflux.dto.NoticeBoardDetailDTO;
import com.example.simplenoticeboardwebflux.dto.User;
import com.example.simplenoticeboardwebflux.entity.NoticeBoard;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class BoardService {

    private final ReactiveBoardRepository reactiveBoardRepository;
    private final ReactiveCommentRepository reactiveCommentRepository;
    private final WebClient webClient;

    public Flux<NoticeBoard> getBoards() {
        return reactiveBoardRepository.findAll();
    }

    public Mono<NoticeBoardDetailDTO> getBoard(Long boardId) {
        return reactiveBoardRepository.findById(boardId).zipWhen(noticeBoard ->
                Mono.zip(webClient.get().uri("/users/" + noticeBoard.getUid()).retrieve().bodyToMono(User.class),
                        reactiveCommentRepository.findAllByBoardId(boardId).collectList()
                )).map(tuple -> new NoticeBoardDetailDTO(tuple.getT1(), tuple.getT2().getT1(), tuple.getT2().getT2()));
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
                            it.setUid(noticeBoard.getUid());
                            it.setUpdatedAt(LocalDateTime.now());

                            return reactiveBoardRepository.save(it);
                        }
                ).map(NoticeBoard::getId);
    }

    public Mono<Long> deleteBoard(long boardId) {
        return reactiveBoardRepository.removeById(boardId);
    }
}
