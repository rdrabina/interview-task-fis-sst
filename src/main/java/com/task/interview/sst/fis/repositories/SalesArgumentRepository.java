package com.task.interview.sst.fis.repositories;

import com.task.interview.sst.fis.entities.SalesArgument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesArgumentRepository extends JpaRepository<SalesArgument, Long> {
}
