package com.compass.datapersistence.service;

import com.compass.datapersistence.entity.PostState;
import com.compass.datapersistence.repository.PostStateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostStateService {
    private final PostStateRepository postStateRepository;

    public PostState createHistory(PostState postState) {
        return postStateRepository.save(postState);
    }


    public List<PostState> getAllHistoryByPostId(Long postId) {
        return postStateRepository.findByPostId(postId);
    }

}
