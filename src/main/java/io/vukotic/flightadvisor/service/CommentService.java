package io.vukotic.flightadvisor.service;

import io.vukotic.flightadvisor.persistence.model.Comment;
import io.vukotic.flightadvisor.persistence.model.User;
import io.vukotic.flightadvisor.persistence.repository.CityRepository;
import io.vukotic.flightadvisor.persistence.repository.CommentRepository;
import io.vukotic.flightadvisor.persistence.repository.UserRepository;
import io.vukotic.flightadvisor.error.exception.CityNotFoundException;
import io.vukotic.flightadvisor.error.exception.CommentNotFoundException;
import io.vukotic.flightadvisor.error.exception.UserNotAuthorizedToHandleCommentException;
import io.vukotic.flightadvisor.error.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CityRepository cityRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public Comment createComment(Long cityId, Comment comment) {
        var city = cityRepository.findById(cityId)
                .orElseThrow(CityNotFoundException::new);
        comment.setCity(city);
        comment.setUser(getCurrentAuthenticatedUser());
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);

    }

    public Comment updateComment(Long commentId, Comment newComment) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        if (!comment.getUser().equals(getCurrentAuthenticatedUser())) {
            throw new UserNotAuthorizedToHandleCommentException();
        }
        comment.setDescription(newComment.getDescription());
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);

    }

    public void deleteComment(Long commentId) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        if (!comment.getUser().equals(getCurrentAuthenticatedUser())) {
            throw new UserNotAuthorizedToHandleCommentException();
        }
        commentRepository.delete(comment);
    }

    private User getCurrentAuthenticatedUser() {
        var username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal().toString();

        return userRepository.findUserByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }
}
