package com.task.interview.sst.fis.services;

import com.task.interview.sst.fis.dtos.BrandModelCarPartDto;
import com.task.interview.sst.fis.dtos.CarPartAvailabilityDto;
import com.task.interview.sst.fis.dtos.CarPartDto;
import com.task.interview.sst.fis.entities.Brand;
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
    private final BrandService brandService;

    @Autowired
    public CarPartServiceImpl(CarPartRepository carPartRepository,
                              BrandService brandService) {
        this.carPartRepository = carPartRepository;
        this.brandService = brandService;
    }

    @Override
    @Transactional
    public Map<String, Map<String, Set<CarPartDto>>> getAllGroupedByModelAndBrand() {
        return brandService.findAll()
                .stream()
                .map(this::createBrandModelCarPartDtos)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(BrandModelCarPartDto::getBrand,
                        Collectors.toMap(BrandModelCarPartDto::getModel, BrandModelCarPartDto::getCarParDtos)));
    }

    private List<BrandModelCarPartDto> createBrandModelCarPartDtos(Brand brand) {
        return brand.getModels()
                .stream()
                .map(model -> createBrandModelCarPartDto(brand, model))
                .collect(Collectors.toList());
    }

    private BrandModelCarPartDto createBrandModelCarPartDto(Brand brand, Model model) {
        Set<CarPartDto> carPartDtos = createCarPartDtos(model);

        return new BrandModelCarPartDto(brand.getName(), model.getName(), carPartDtos);
    }

    private Set<CarPartDto> createCarPartDtos(Model model) {
        return model.getCarParts()
                .stream()
                .map(this::createCarPartDto)
                .collect(Collectors.toSet());
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
