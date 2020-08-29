package com.task.interview.sst.fis.services;

import com.task.interview.sst.fis.annotations.Logger;
import com.task.interview.sst.fis.dtos.BrandModelCarPartDto;
import com.task.interview.sst.fis.dtos.CarPartAvailabilityDto;
import com.task.interview.sst.fis.dtos.CarPartDto;
import com.task.interview.sst.fis.dtos.ServiceActionDto;
import com.task.interview.sst.fis.entities.*;
import com.task.interview.sst.fis.exceptions.ResourceNotFoundException;
import com.task.interview.sst.fis.repositories.CarPartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.task.interview.sst.fis.utils.DateUtils.addDaysToDate;

@Service
public class CarPartServiceImpl implements CarPartService {

    private final CarPartRepository carPartRepository;
    private final ServiceActionService serviceActionService;
    private final ServiceActionNameService serviceActionNameService;

    @Autowired
    public CarPartServiceImpl(CarPartRepository carPartRepository,
                              ServiceActionService serviceActionService,
                              ServiceActionNameService serviceActionNameService) {
        this.carPartRepository = carPartRepository;
        this.serviceActionService = serviceActionService;
        this.serviceActionNameService = serviceActionNameService;
    }

    @Override
    @Transactional
    @Logger
    public Map<String, Map<String, Set<CarPartDto>>> getAllGroupedByBrandAndModel(String carPartNameDescriptionFilter) {
        return groupCarPartsByBrandAndModel(
                carPartRepository.findAllByCarPartDetailsNameContainingIgnoreCaseOrCarPartDetailsDescriptionContainingIgnoreCase(
                        carPartNameDescriptionFilter, carPartNameDescriptionFilter
                )
        );
    }

    private Map<String, Map<String, Set<CarPartDto>>> groupCarPartsByBrandAndModel(List<CarPart> carParts) {
        return carParts.stream()
                .map(this::createBrandModelCarPartDtos)
                .flatMap(Collection::stream)
                .collect(
                        Collectors.groupingBy(BrandModelCarPartDto::getBrand,
                                Collectors.groupingBy(BrandModelCarPartDto::getModel,
                                        Collectors.mapping(BrandModelCarPartDto::getCarPartDto, Collectors.toSet())
                                )
                        )
                );
    }

    private List<BrandModelCarPartDto> createBrandModelCarPartDtos(CarPart carPart) {
        return carPart.getModels()
                .stream()
                .map(model -> createBrandModelCarPartDto(carPart, model))
                .collect(Collectors.toList());
    }

    private BrandModelCarPartDto createBrandModelCarPartDto(CarPart carPart, Model model) {
        return BrandModelCarPartDto.builder()
                .brand(model.getBrandName())
                .model(model.getName())
                .carPartDto(CarPartDto.createCarPartDto(carPart))
                .build();
    }

    @Override
    @Transactional
    @Logger
    public CarPartAvailabilityDto checkAvailabilityAndShipmentDate(Long id) {
        return carPartRepository.findById(id)
                .map(this::createCarPartAvailabilityDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private CarPartAvailabilityDto createCarPartAvailabilityDto(CarPart carPart) {
        CarPartDetails carPartDetails = carPart.getCarPartDetails();

        return new CarPartAvailabilityDto(carPartDetails.isOnStock(), addDaysToDate(carPartDetails.getShipmentWithinDays()));
    }

    @Override
    @Transactional
    @Logger
    public int modifyCarPart(Long id, CarPartDto carPartDto) {
        return carPartRepository.modifyCarPartDescription(carPartDto.getDescription(), id);
    }

    @Override
    @Transactional
    @Logger
    public void addServiceAction(Long id, ServiceActionDto serviceActionDto) {
        CarPart carPart = carPartRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        ServiceActionName serviceActionName = serviceActionNameService.findByName(serviceActionDto.getName())
                .orElseGet(() -> createServiceActionName(serviceActionDto));

        ServiceAction serviceAction = createServiceAction(carPart, serviceActionName, serviceActionDto);

        save(carPart, serviceAction, serviceActionName);
    }

    private ServiceActionName createServiceActionName(ServiceActionDto serviceActionDto) {
        ServiceActionName serviceActionName = new ServiceActionName();
        serviceActionName.setValue(serviceActionDto.getName());
        serviceActionName.setServiceActions(new ArrayList<>());

        return serviceActionName;
    }

    private ServiceAction createServiceAction(CarPart carPart, ServiceActionName serviceActionName,
                                              ServiceActionDto serviceActionDto) {
        ServiceAction serviceAction = new ServiceAction();
        serviceAction.setServiceActionName(serviceAction, serviceActionName);
        serviceAction.setCarPart(serviceAction, carPart);
        serviceAction.setStartDate(serviceActionDto.getStartDate());
        serviceAction.setEndDate(serviceActionDto.getEndDate());

        return serviceAction;
    }

    private void save(CarPart carPart, ServiceAction serviceAction, ServiceActionName serviceActionName) {
        serviceActionNameService.save(serviceActionName);
        serviceActionService.save(serviceAction);
        carPartRepository.save(carPart);
    }

    @Override
    @Transactional
    @Logger
    public int deleteSalesArguments(Long id) {
        CarPart carPart = carPartRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        int size = carPart.removeAllSalesArguments();
        carPartRepository.save(carPart);

        return size;
    }

}
