package com.task.interview.sst.fis.services;

import com.task.interview.sst.fis.entities.ServiceActionName;

import java.util.Optional;

public interface ServiceActionNameService {

    ServiceActionName save(ServiceActionName serviceActionName);

    Optional<ServiceActionName> findByName(String name);

}
