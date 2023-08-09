package com.compass.datapersistence.repository;

import com.compass.datapersistence.entity.PostState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostStateRepository extends JpaRepository<PostState, Integer> {
        List<PostState> findByPostId(Long postId);
}
