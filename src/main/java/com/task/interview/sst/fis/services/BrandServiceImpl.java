package com.task.interview.sst.fis.services;

import com.task.interview.sst.fis.annotations.Logger;
import com.task.interview.sst.fis.dtos.BrandModelCarPartDto;
import com.task.interview.sst.fis.dtos.CarPartDto;
import com.task.interview.sst.fis.entities.Brand;
import com.task.interview.sst.fis.entities.Model;
import com.task.interview.sst.fis.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    @Transactional
    @Logger
    public Map<String, Map<String, Set<CarPartDto>>> getBrandByNameWithModelsAndCarParts(String name) {
        return brandRepository.findAllByNameIgnoreCase(name)
                .stream()
                .map(this::createBrandModelCarPartDtos)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(BrandModelCarPartDto::getBrand,
                        Collectors.toMap(BrandModelCarPartDto::getModel, BrandModelCarPartDto::getCarPartDtos)));
    }

    private List<BrandModelCarPartDto> createBrandModelCarPartDtos(Brand brand) {
        return brand.getModels()
                .stream()
                .map(model -> createBrandModelCarPartDto(brand, model))
                .collect(Collectors.toList());
    }

    private BrandModelCarPartDto createBrandModelCarPartDto(Brand brand, Model model) {
        Set<CarPartDto> carPartDtos = createCarPartDtos(model);

        return BrandModelCarPartDto.builder()
                .brand(brand.getName())
                .model(model.getName())
                .carPartDtos(carPartDtos)
                .build();
    }

    private Set<CarPartDto> createCarPartDtos(Model model) {
        return model.getCarParts()
                .stream()
                .map(CarPartDto::createCarPartDto)
                .collect(Collectors.toSet());
    }

}
