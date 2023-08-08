package com.compass.datapersistence.repository;

import com.compass.datapersistence.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
        List<History> findByPostId(Long postId);
}
