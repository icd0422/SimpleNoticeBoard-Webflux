package com.example.simplenoticeboardwebflux;

import com.example.simplenoticeboardwebflux.entity.Comment;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

public interface ReactiveCommentRepository extends ReactiveSortingRepository<Comment, Long> {

    Flux<Comment> findAllByBoardId(Long boardId);
}
