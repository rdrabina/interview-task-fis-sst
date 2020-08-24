package com.task.interview.sst.fis.services;

import com.task.interview.sst.fis.dtos.CarPartAvailabilityDto;
import com.task.interview.sst.fis.dtos.CarPartDto;

import java.util.Map;
import java.util.Set;

public interface CarPartService {

    Map<String, Map<String, Set<CarPartDto>>> getAllGroupedByModelAndBrand();

    CarPartAvailabilityDto checkAvailabilityAndShipmentDate(Long id);

}
