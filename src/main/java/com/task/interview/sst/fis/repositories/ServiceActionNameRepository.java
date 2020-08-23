package com.task.interview.sst.fis.repositories;

import com.task.interview.sst.fis.entities.ServiceActionName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceActionNameRepository extends JpaRepository<ServiceActionName, Long> {
}
