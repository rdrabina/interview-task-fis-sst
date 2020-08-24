package com.task.interview.sst.fis.services;

import com.task.interview.sst.fis.dtos.ServiceActionDto;
import com.task.interview.sst.fis.entities.ServiceAction;
import com.task.interview.sst.fis.repositories.ServiceActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceActionServiceImpl implements ServiceActionService {

    private final ServiceActionRepository serviceActionRepository;

    @Autowired
    public ServiceActionServiceImpl(ServiceActionRepository serviceActionRepository) {
        this.serviceActionRepository = serviceActionRepository;
    }

    @Override
    @Transactional
    public List<ServiceActionDto> findAllBetweenDates(Date startDate, Date endDate) {
        return serviceActionRepository.findAllByStartDateBetween(startDate, endDate)
                .stream()
                .map(this::createServiceActionDto)
                .collect(Collectors.toList());
    }

    private ServiceActionDto createServiceActionDto(ServiceAction serviceAction) {
        return ServiceActionDto.builder()
                .name(serviceAction.getServiceActionNameValue())
                .carPartName(serviceAction.getCarPartName())
                .startDate(serviceAction.getStartDate())
                .endDate(serviceAction.getEndDate())
                .build();
    }
}
