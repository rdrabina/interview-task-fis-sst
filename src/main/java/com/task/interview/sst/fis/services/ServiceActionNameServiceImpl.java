package com.task.interview.sst.fis.services;

import com.task.interview.sst.fis.annotations.Logger;
import com.task.interview.sst.fis.entities.ServiceActionName;
import com.task.interview.sst.fis.repositories.ServiceActionNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ServiceActionNameServiceImpl implements ServiceActionNameService {

    private final ServiceActionNameRepository serviceActionNameRepository;

    @Autowired
    public ServiceActionNameServiceImpl(ServiceActionNameRepository serviceActionNameRepository) {
        this.serviceActionNameRepository = serviceActionNameRepository;
    }

    @Override
    @Transactional
    @Logger
    public ServiceActionName save(ServiceActionName serviceActionName) {
        return serviceActionNameRepository.save(serviceActionName);
    }

    @Override
    @Transactional
    @Logger
    public Optional<ServiceActionName> findByName(String name) {
        return serviceActionNameRepository.findByValue(name);
    }

}
