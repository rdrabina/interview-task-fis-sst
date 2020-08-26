package com.task.interview.sst.fis.repositories;

import com.task.interview.sst.fis.entities.ServiceActionName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceActionNameRepository extends JpaRepository<ServiceActionName, Long> {

    Optional<ServiceActionName> findByValue(String name);

}
