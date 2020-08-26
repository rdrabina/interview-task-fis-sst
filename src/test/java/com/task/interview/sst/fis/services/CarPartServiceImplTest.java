package com.task.interview.sst.fis.services;

import com.task.interview.sst.fis.dtos.CarPartAvailabilityDto;
import com.task.interview.sst.fis.dtos.CarPartDto;
import com.task.interview.sst.fis.entities.CarPart;
import com.task.interview.sst.fis.entities.ServiceAction;
import com.task.interview.sst.fis.entities.ServiceActionName;
import com.task.interview.sst.fis.exceptions.ResourceNotFoundException;
import com.task.interview.sst.fis.repositories.CarPartRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.task.interview.sst.fis.test.utils.ObjectFactory.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CarPartServiceImplTest {

    @InjectMocks
    private CarPartServiceImpl carPartService;

    @Mock
    private CarPartRepository carPartRepository;
    @Mock
    private ServiceActionService serviceActionService;
    @Mock
    private ServiceActionNameService serviceActionNameService;

    @Test
    public void getAllGroupedByBrandAndModel() {
        when(carPartRepository.findAll()).thenReturn(createCarPartList());

        Map<String, Map<String, Set<CarPartDto>>> allGroupedByBrandAndModel = carPartService.getAllGroupedByBrandAndModel();

        verify(carPartRepository, times(1)).findAll();
        Assertions.assertEquals(allGroupedByBrandAndModel, createGroupedByBrandAndModelMap());
    }

    @Test
    public void getAllGroupedByBrandAndModelWithFilterByName() {
        when(carPartRepository.findAllByCarPartDetailsNameContainingIgnoreCaseOrCarPartDetailsDescriptionContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(createCarPartList());

        Map<String, Map<String, Set<CarPartDto>>> allGroupedByBrandAndModelAndFilter = carPartService.getAllGroupedByBrandAndModelWithFilter("nameOrDescription");

        verify(carPartRepository, times(1))
                .findAllByCarPartDetailsNameContainingIgnoreCaseOrCarPartDetailsDescriptionContainingIgnoreCase("nameOrDescription", "nameOrDescription");
        Assertions.assertEquals(allGroupedByBrandAndModelAndFilter, createGroupedByBrandAndModelMap());
    }

    @Test
    public void checkAvailabilityAndShipmentDate() {
        when(carPartRepository.findById(anyLong()))
                .thenReturn(
                        Optional.of(createCarPartList().get(0))
                );

        CarPartAvailabilityDto carPartAvailabilityDto = carPartService.checkAvailabilityAndShipmentDate(1L);

        verify(carPartRepository).findById(1L);

        Assertions.assertTrue(carPartAvailabilityDto.isOnStock());
        Assertions.assertTrue(
                (Math.abs(createCarPartAvailabilityDto().getPossibleShipmentDate().getTime()
                        - carPartAvailabilityDto.getPossibleShipmentDate().getTime()) < 1000)
        );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void checkAvailabilityAndShipmentDateWithoutRecord() {
        when(carPartRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        carPartService.checkAvailabilityAndShipmentDate(1L);
    }

    @Test
    public void modifyCarPart() {
        when(carPartRepository.modifyCarPartDescription(anyString(), anyLong()))
                .thenReturn(5);

        int numberOfModifiedCarParts = carPartService.modifyCarPart(1L, createCarPartDto());

        verify(carPartRepository).modifyCarPartDescription("car part description test", 1L);
        Assertions.assertEquals(numberOfModifiedCarParts, 5);
    }

    @Test
    public void addServiceAction() {
        when(carPartRepository.findById(anyLong()))
                .thenReturn(
                        Optional.of(createCarPartList().get(0))
                );
        when(serviceActionNameService.findByName(anyString()))
                .thenReturn(Optional.of(new ServiceActionName()));

        carPartService.addServiceAction(1L, createServiceActionDto());

        verify(serviceActionNameService, times(1)).save(any(ServiceActionName.class));
        verify(serviceActionService, times(1)).save(any(ServiceAction.class));
        verify(carPartRepository, times(1)).save(any(CarPart.class));
    }

    @Test
    public void addServiceActionWithNonExistingName() {
        when(carPartRepository.findById(anyLong()))
                .thenReturn(
                        Optional.of(createCarPartList().get(0))
                );
        when(serviceActionNameService.findByName(anyString()))
                .thenReturn(Optional.empty());

        carPartService.addServiceAction(1L, createServiceActionDto());

        verify(serviceActionNameService, times(1)).save(any(ServiceActionName.class));
        verify(serviceActionService, times(1)).save(any(ServiceAction.class));
        verify(carPartRepository, times(1)).save(any(CarPart.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void addServiceActionWithException() {
        when(carPartRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        carPartService.addServiceAction(1L, createServiceActionDto());
    }

    @Test
    public void deleteSalesArguments() {
        when(carPartRepository.findById(anyLong()))
                .thenReturn(
                        Optional.of(createCarPartList().get(0))
                );

        int numberOfDeletedCarParts = carPartService.deleteSalesArguments(1L);

        Assertions.assertEquals(numberOfDeletedCarParts, 1);
        verify(carPartRepository, times(1)).findById(1L);
        verify(carPartRepository, times(1)).save(any(CarPart.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteSalesArgumentsWithException() {
        when(carPartRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        carPartService.deleteSalesArguments(1L);
    }

}