package io.vukotic.flightadvisor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
