package com.task.interview.sst.fis.services;

import com.task.interview.sst.fis.dtos.CarPartAvailabilityDto;
import com.task.interview.sst.fis.dtos.CarPartDto;
import com.task.interview.sst.fis.dtos.ServiceActionDto;

import java.util.Map;
import java.util.Set;

public interface CarPartService {

    Map<String, Map<String, Set<CarPartDto>>> getAllGroupedByBrandAndModel(String carPartNameDescriptionFilter);

    CarPartAvailabilityDto checkAvailabilityAndShipmentDate(Long id);

    int modifyCarPart(Long id, CarPartDto carPartDto);

    void addServiceAction(Long id, ServiceActionDto serviceActionDto);

    int deleteSalesArguments(Long id);

}
