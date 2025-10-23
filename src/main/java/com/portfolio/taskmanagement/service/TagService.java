package com.portfolio.taskmanagement.service;

import com.portfolio.taskmanagement.dto.TagDTO;
import com.portfolio.taskmanagement.exception.DuplicateResourceException;
import com.portfolio.taskmanagement.exception.ResourceNotFoundException;
import com.portfolio.taskmanagement.model.Tag;
import com.portfolio.taskmanagement.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<TagDTO> getAllTags() {
        return tagRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TagDTO getTagById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));
        return convertToDTO(tag);
    }

    @Transactional
    public TagDTO createTag(TagDTO tagDTO) {
        if (tagRepository.existsByName(tagDTO.getName())) {
            throw new DuplicateResourceException("Tag already exists: " + tagDTO.getName());
        }

        Tag tag = new Tag();
        tag.setName(tagDTO.getName());

        Tag savedTag = tagRepository.save(tag);
        return convertToDTO(savedTag);
    }

    @Transactional
    public TagDTO updateTag(Long id, TagDTO tagDTO) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));

        if (!tag.getName().equals(tagDTO.getName()) &&
            tagRepository.existsByName(tagDTO.getName())) {
            throw new DuplicateResourceException("Tag already exists: " + tagDTO.getName());
        }

        tag.setName(tagDTO.getName());

        Tag updatedTag = tagRepository.save(tag);
        return convertToDTO(updatedTag);
    }

    @Transactional
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));
        tagRepository.delete(tag);
    }

    private TagDTO convertToDTO(Tag tag) {
        TagDTO dto = new TagDTO();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        return dto;
    }
}
