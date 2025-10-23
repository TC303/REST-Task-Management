package com.portfolio.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {

    private Long id;

    @NotBlank(message = "Tag name is required")
    private String name;
}
