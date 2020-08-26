package com.task.interview.sst.fis.services;


import com.task.interview.sst.fis.dtos.ServiceActionDto;
import com.task.interview.sst.fis.entities.CarPart;
import com.task.interview.sst.fis.entities.CarPartDetails;
import com.task.interview.sst.fis.entities.ServiceAction;
import com.task.interview.sst.fis.entities.ServiceActionName;
import com.task.interview.sst.fis.repositories.ServiceActionRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceActionServiceImplTest {

    @InjectMocks
    private ServiceActionServiceImpl serviceActionService;

    @Mock
    private ServiceActionRepository serviceActionRepository;

    @Test
    public void findAllBetweenDates() {
        Date date = new Date();
        when(serviceActionRepository.findAllByStartDateBetween(any(Date.class), any(Date.class)))
                .thenReturn(createServiceActionList(date));

        List<ServiceActionDto> allBetweenDates = serviceActionService.findAllBetweenDates(date, date);

        verify(serviceActionRepository, times(1)).findAllByStartDateBetween(date, date);
        Assertions.assertEquals(allBetweenDates, createServiceActionDtoList(date));
    }

    private List<ServiceAction> createServiceActionList(Date date) {
        ServiceActionName serviceActionName = new ServiceActionName();
        serviceActionName.setServiceActions(new ArrayList<>());
        serviceActionName.setValue("service action name test");

        CarPart carPart = new CarPart();
        carPart.setServiceActions(new ArrayList<>());
        CarPartDetails carPartDetails = new CarPartDetails();
        carPartDetails.setName("car part name test");
        carPart.setCarPartDetails(carPartDetails);

        ServiceAction serviceAction = new ServiceAction();
        serviceAction.setCarPart(carPart);
        serviceAction.setServiceActionName(serviceAction, serviceActionName);
        serviceAction.setStartDate(date);
        serviceAction.setEndDate(date);

        return Collections.singletonList(serviceAction);
    }

    private List<ServiceActionDto> createServiceActionDtoList(Date date) {
        ServiceActionDto serviceActionDto = new ServiceActionDto();
        serviceActionDto.setName("service action name test");
        serviceActionDto.setStartDate(date);
        serviceActionDto.setEndDate(date);
        serviceActionDto.setCarPartName("car part name test");

        return Collections.singletonList(serviceActionDto);
    }

    @Test
    public void save() {
        ServiceAction serviceAction = new ServiceAction();
        when(serviceActionRepository.save(any(ServiceAction.class)))
                .thenReturn(serviceAction);

        serviceActionService.save(serviceAction);

        verify(serviceActionRepository).save(serviceAction);
    }

}