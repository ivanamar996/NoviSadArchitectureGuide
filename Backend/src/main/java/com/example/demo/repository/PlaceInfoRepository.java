package com.example.demo.repository;

import com.example.demo.model.PlaceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceInfoRepository extends JpaRepository<PlaceInfo, Integer> {
}
