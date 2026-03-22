package com.project.currency_converter.repository;

import com.project.currency_converter.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionHistoryRepository extends JpaRepository<History, Long> {
}
