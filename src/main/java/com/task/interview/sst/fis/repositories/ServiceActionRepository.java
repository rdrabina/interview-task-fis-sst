package com.task.interview.sst.fis.repositories;

import com.task.interview.sst.fis.entities.ServiceAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceActionRepository extends JpaRepository<ServiceAction, Long> {
}
