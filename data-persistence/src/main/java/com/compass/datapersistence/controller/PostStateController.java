package com.compass.datapersistence.controller;

import com.compass.datapersistence.dto.PostStateDTO;
import com.compass.datapersistence.entity.PostState;
import com.compass.datapersistence.service.PostStateService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/status")
public class PostStateController {

    private final PostStateService postStateService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<PostStateDTO> createHistory(@RequestBody PostStateDTO postStateDTO) {
        PostState postStateEntity = modelMapper.map(postStateDTO, PostState.class);
        PostState createdPostState = postStateService.createHistory(postStateEntity);
        PostStateDTO responseHistory = modelMapper.map(createdPostState, PostStateDTO.class);

        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdPostState.getId())
                        .toUri()
        ).body(responseHistory);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<PostStateDTO>> getAllHistories(@PathVariable Long postId) {
        List<PostState> histories = postStateService.getAllHistoryByPostId(postId);
        List<PostStateDTO> postStateDTOS = histories.stream()
                .map(postState -> modelMapper.map(postState, PostStateDTO.class))
                .toList();
        return ResponseEntity.ok(postStateDTOS);
    }

}
