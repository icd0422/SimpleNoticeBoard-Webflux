package com.example.simplenoticeboardwebflux;

import com.example.simplenoticeboardwebflux.entity.NoticeBoard;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class BoardService {

    private final ReactiveBoardRepository reactiveBoardRepository;

    public Flux<NoticeBoard> getBoards() {
        return reactiveBoardRepository.findAll();
    }

    public Mono<NoticeBoard> registerBoard(NoticeBoard noticeBoard) {
        return reactiveBoardRepository.save(noticeBoard);
    }
}
