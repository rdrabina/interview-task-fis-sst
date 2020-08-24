package com.task.interview.sst.fis.repositories;

import com.task.interview.sst.fis.entities.ServiceAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ServiceActionRepository extends JpaRepository<ServiceAction, Long> {

    List<ServiceAction> findAllByStartDateBetween(Date startDate, Date endDate);

}
