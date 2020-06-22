package com.example.demo.repository;

import com.example.demo.model.RouteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteInfoRepository extends JpaRepository<RouteInfo, Integer> {
}
