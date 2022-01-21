package io.vukotic.flightadvisor.persistence.repository;

import io.vukotic.flightadvisor.persistence.model.City;
import io.vukotic.flightadvisor.persistence.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    List<Comment> findByCity(City city, Pageable pageable);
}
