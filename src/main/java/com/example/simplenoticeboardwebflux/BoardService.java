package com.example.simplenoticeboardwebflux;

import com.example.simplenoticeboardwebflux.dto.NoticeBoardDetailDTO;
import com.example.simplenoticeboardwebflux.dto.User;
import com.example.simplenoticeboardwebflux.entity.Comment;
import com.example.simplenoticeboardwebflux.entity.NoticeBoard;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BoardService {

    private final ReactiveBoardRepository reactiveBoardRepository;
    private final ReactiveCommentRepository reactiveCommentRepository;
    private final WebClient webClient;

    public Flux<NoticeBoard> getBoards() {
        return reactiveBoardRepository.findAll();
    }

//    public Mono<NoticeBoardDetailDTO> getBoard(Long boardId) {
//        return reactiveBoardRepository.findById(boardId).zipWhen(noticeBoard ->
//                        Mono.zip(webClient.get().uri("/users/" + noticeBoard.getUid()).retrieve().bodyToMono(User.class),
//                                reactiveCommentRepository.findAllByBoardId(boardId).collectList()
//                        ))
//                .map(tuple -> {
//                    NoticeBoard noticeBoard = tuple.getT1();
//                    User user = tuple.getT2().getT1();
//                    List<Comment> commentList = tuple.getT2().getT2();
//                    commentList = commentList.stream().filter(comment -> comment.getGender().equals(user.getGender())).collect(Collectors.toList());
//                    return new NoticeBoardDetailDTO(noticeBoard, user, commentList);
//                });
//    }

    public Mono<NoticeBoardDetailDTO> getBoard2(Long boardId) {
        return reactiveBoardRepository.findById(boardId).zipWhen(noticeBoard -> {
                    return reactiveCommentRepository.findAllByBoardId(boardId).collectList();
                }
        ).zipWhen(tuple -> {
            List<Comment> commentList = tuple.getT2();
            String uids = commentList.stream().map(it -> it.getUid().toString()).reduce("", (a, b) -> {
                if (a.equals("")) return a + b;
                return (a + "," + b);
            });
            NoticeBoard noticeBoard = tuple.getT1();
            uids += "," + noticeBoard.getUid();
            return webClient.get().uri("/users?uids=" + uids).retrieve().bodyToFlux(User.class).collectList();
        }).map(tuple -> {
                    NoticeBoard noticeBoard = tuple.getT1().getT1();
                    List<Comment> commentList = tuple.getT1().getT2();
                    List<User> userList = tuple.getT2();

                    Map<Long, List<User>> userMap = userList.stream().collect(Collectors.groupingBy(User::getId));
                    User noticeBoardUser = userMap.get(noticeBoard.getUid()).get(0);
                    List<Comment> commentListOnSameNoticeBoardUserGender = commentList.stream().filter(it -> userMap.get(it.getUid()).get(0).getGender().equals(noticeBoardUser.getGender())).collect(Collectors.toList());

                    return new NoticeBoardDetailDTO(noticeBoard, noticeBoardUser, commentListOnSameNoticeBoardUserGender);
                }
        );
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
