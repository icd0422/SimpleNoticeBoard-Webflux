package com.example.simplenoticeboardwebflux;

import com.example.simplenoticeboardwebflux.entity.NoticeBoard;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveBoardRepository extends ReactiveSortingRepository<NoticeBoard, Long> {

}
