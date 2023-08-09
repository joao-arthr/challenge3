package com.compass.datapersistence.controller;

import com.compass.datapersistence.dto.HistoryDTO;
import com.compass.datapersistence.entity.History;
import com.compass.datapersistence.service.HistoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/history")
public class HistoryController {

    private final HistoryService historyService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<HistoryDTO> createHistory(@RequestBody HistoryDTO historyDTO) {
        History historyEntity = modelMapper.map(historyDTO, History.class);
        History createdHistory = historyService.createHistory(historyEntity);
        HistoryDTO responseHistory = modelMapper.map(createdHistory, HistoryDTO.class);

        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdHistory.getId())
                        .toUri()
        ).body(responseHistory);
    }


    @GetMapping("/{postId}")
    public ResponseEntity<List<HistoryDTO>> getAllHistories(@PathVariable Long postId) {
        List<History> histories = historyService.getAllHistoryByPostId(postId);
        List<HistoryDTO> historyDTOs = histories.stream()
                .map(history -> modelMapper.map(history, HistoryDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(historyDTOs);
    }

}
