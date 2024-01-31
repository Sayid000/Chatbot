package com.main.repository;


import com.main.entity.LineMessageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineMessageLogRepository extends JpaRepository<LineMessageLog, Long> {
}
