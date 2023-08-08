package com.compass.datapersistence.service;

import com.compass.datapersistence.entity.History;
import com.compass.datapersistence.repository.HistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    public History createHistory(History history) {
        return historyRepository.save(history);
    }


    public List<History> getAllHistoryByPostId(Long postId) {
        return historyRepository.findByPostId(postId);
    }

}
