package com.example.simplenoticeboardwebflux;

import com.example.simplenoticeboardwebflux.entity.NoticeBoard;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveBoardRepository extends ReactiveSortingRepository<NoticeBoard, Long> {

    @Modifying
    @Query("DELETE FROM tb_notice_board WHERE id = :boardId")
    Mono<Long> removeById(Long boardId);
}
