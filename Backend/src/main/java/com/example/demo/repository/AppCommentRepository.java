package com.example.demo.repository;

import com.example.demo.model.AppComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppCommentRepository extends JpaRepository<AppComment, Integer> {
}
