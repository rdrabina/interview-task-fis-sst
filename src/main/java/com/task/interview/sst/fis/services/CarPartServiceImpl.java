package com.task.interview.sst.fis.services;

import com.task.interview.sst.fis.dtos.BrandModelCarPartDto;
import com.task.interview.sst.fis.dtos.CarPartAvailabilityDto;
import com.task.interview.sst.fis.dtos.CarPartDto;
import com.task.interview.sst.fis.entities.CarPart;
import com.task.interview.sst.fis.entities.CarPartDetails;
import com.task.interview.sst.fis.entities.Model;
import com.task.interview.sst.fis.exceptions.ResourceNotFoundException;
import com.task.interview.sst.fis.repositories.CarPartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarPartServiceImpl implements CarPartService {

    private final CarPartRepository carPartRepository;

    @Autowired
    public CarPartServiceImpl(CarPartRepository carPartRepository) {
        this.carPartRepository = carPartRepository;
    }

    @Override
    @Transactional
    public Map<String, Map<String, Set<CarPartDto>>> getAllGroupedByBrandAndModel() {
        return groupCarPartsByBrandAndModel(carPartRepository.findAll());
    }

    private Map<String, Map<String, Set<CarPartDto>>> groupCarPartsByBrandAndModel(List<CarPart> carParts) {
        return carParts.stream()
                .map(this::createBrandModelCarPartDtos)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(BrandModelCarPartDto::getBrand,
                        Collectors.groupingBy(BrandModelCarPartDto::getModel,
                                Collectors.mapping(BrandModelCarPartDto::getCarPartDto, Collectors.toSet()))));
    }

    private List<BrandModelCarPartDto> createBrandModelCarPartDtos(CarPart carPart) {
        return carPart.getModels()
                .stream()
                .map(model -> createBrandModelCarPartDto(carPart, model))
                .collect(Collectors.toList());
    }

    private BrandModelCarPartDto createBrandModelCarPartDto(CarPart carPart, Model model) {
        return new BrandModelCarPartDto(model.getBrandName(), model.getName(), createCarPartDto(carPart));
    }

    private CarPartDto createCarPartDto(CarPart carPart) {
        CarPartDetails carPartDetails = carPart.getCarPartDetails();

        return CarPartDto.builder()
                .name(carPartDetails.getName())
                .description(carPartDetails.getDescription())
                .price(carPartDetails.getPrice())
                .onStock(carPartDetails.isOnStock())
                .shipmentWithinDays(carPartDetails.getShipmentWithinDays())
                .build();
    }

    @Override
    @Transactional
    public Map<String, Map<String, Set<CarPartDto>>> getAllGroupedByBrandAndModelWithFilter(String carPartNameDescriptionFilter) {
        return groupCarPartsByBrandAndModel(
                carPartRepository.findAllByCarPartDetailsNameContainingIgnoreCaseOrCarPartDetailsDescriptionContainingIgnoreCase(
                        carPartNameDescriptionFilter, carPartNameDescriptionFilter
                )
        );
    }

    @Override
    @Transactional
    public CarPartAvailabilityDto checkAvailabilityAndShipmentDate(Long id) {
        return carPartRepository.findById(id)
                .map(this::createCarPartAvailabilityDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private CarPartAvailabilityDto createCarPartAvailabilityDto(CarPart carPart) {
        CarPartDetails carPartDetails = carPart.getCarPartDetails();

        return new CarPartAvailabilityDto(carPartDetails.isOnStock(), createPossibleShipmentDate(carPartDetails));
    }

    private Date createPossibleShipmentDate(CarPartDetails carPartDetails) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, carPartDetails.getShipmentWithinDays());

        return calendar.getTime();
    }

}
