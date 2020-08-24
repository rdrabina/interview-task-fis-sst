package com.task.interview.sst.fis.controllers;

import com.task.interview.sst.fis.dtos.CarPartAvailabilityDto;
import com.task.interview.sst.fis.dtos.CarPartDto;
import com.task.interview.sst.fis.services.CarPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/car-parts")
public class CarPartController {

    private final CarPartService carPartService;

    @Autowired
    public CarPartController(CarPartService carPartService) {
        this.carPartService = carPartService;
    }

    @GetMapping("/grouped-by-model-and-brand")
    public ResponseEntity<Map<String, Map<String, Set<CarPartDto>>>> getAllGroupedByModelAndBrand() {
        return new ResponseEntity<>(
                carPartService.getAllGroupedByModelAndBrand(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<CarPartAvailabilityDto> checkAvailability(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(
                carPartService.checkAvailabilityAndShipmentDate(id),
                HttpStatus.OK
        );
    }

}
