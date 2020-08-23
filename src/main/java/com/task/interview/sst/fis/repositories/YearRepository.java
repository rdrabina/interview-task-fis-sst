package com.task.interview.sst.fis.repositories;

import com.task.interview.sst.fis.entities.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearRepository extends JpaRepository<Year, Long> {
}
