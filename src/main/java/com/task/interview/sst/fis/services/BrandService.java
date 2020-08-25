package com.task.interview.sst.fis.services;

import com.task.interview.sst.fis.dtos.CarPartDto;

import java.util.Map;
import java.util.Set;

public interface BrandService {

    Map<String, Map<String, Set<CarPartDto>>> getBrandByNameWithModelsAndCarParts(String name);

}
