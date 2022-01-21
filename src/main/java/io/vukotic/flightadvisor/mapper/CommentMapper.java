package io.vukotic.flightadvisor.mapper;

import io.vukotic.flightadvisor.persistence.model.Comment;
import io.vukotic.flightadvisor.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final ModelMapper modelMapper;

    public Comment convertCommentToEntity(CommentDto comment) {
        return modelMapper.map(comment, Comment.class);
    }

    public CommentDto convertCommentToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

}
