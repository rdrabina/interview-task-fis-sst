package com.task.interview.sst.fis.services;

import com.task.interview.sst.fis.dtos.ServiceActionDto;
import com.task.interview.sst.fis.entities.ServiceAction;

import java.util.Date;
import java.util.List;

public interface ServiceActionService {

    List<ServiceActionDto> findAllBetweenDates(Date startDate, Date endDate);

    ServiceAction save(ServiceAction serviceAction);

}
