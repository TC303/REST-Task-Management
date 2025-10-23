package com.portfolio.taskmanagement.controller;

import com.portfolio.taskmanagement.dto.TagDTO;
import com.portfolio.taskmanagement.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Tag(name = "Tag Management", description = "APIs for managing tags")
public class TagController {

    private final TagService tagService;

    @GetMapping
    @Operation(summary = "Get all tags")
    public ResponseEntity<List<TagDTO>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get tag by ID")
    public ResponseEntity<TagDTO> getTagById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTagById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new tag")
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody TagDTO tagDTO) {
        return new ResponseEntity<>(tagService.createTag(tagDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing tag")
    public ResponseEntity<TagDTO> updateTag(@PathVariable Long id, @Valid @RequestBody TagDTO tagDTO) {
        return ResponseEntity.ok(tagService.updateTag(id, tagDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a tag")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
