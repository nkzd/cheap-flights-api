package io.vukotic.flightadvisor.controller;

import io.vukotic.flightadvisor.service.CommentService;
import io.vukotic.flightadvisor.dto.CommentDto;
import io.vukotic.flightadvisor.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping(value = "/cities/{cityId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CommentDto createComment(@PathVariable Long cityId, @Valid @RequestBody CommentDto comment) {
        var commentEntity = commentMapper.convertCommentToEntity(comment);
        return commentMapper.convertCommentToDto(commentService.createComment(cityId, commentEntity));
    }

    @PutMapping(value = "/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommentDto updateComment(@PathVariable Long commentId, @Valid @RequestBody CommentDto comment) {
        var commentEntity = commentMapper.convertCommentToEntity(comment);
        return commentMapper.convertCommentToDto(commentService.updateComment(commentId, commentEntity));
    }

    @DeleteMapping(value = "/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
