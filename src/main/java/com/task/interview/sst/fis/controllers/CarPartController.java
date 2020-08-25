package com.task.interview.sst.fis.controllers;

import com.task.interview.sst.fis.dtos.CarPartAvailabilityDto;
import com.task.interview.sst.fis.dtos.CarPartDto;
import com.task.interview.sst.fis.dtos.ServiceActionDto;
import com.task.interview.sst.fis.services.CarPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/grouped-by-brand-and-model")
    public ResponseEntity<Map<String, Map<String, Set<CarPartDto>>>> getAllGroupedByBrandAndModel() {
        return new ResponseEntity<>(
                carPartService.getAllGroupedByBrandAndModel(),
                HttpStatus.OK
        );
    }

    @GetMapping("/grouped-by-brand-and-model/{car-part-name-description-filter}")
    public ResponseEntity<Map<String, Map<String, Set<CarPartDto>>>> getAllGroupedByBrandAndModelWithFilter(
            @PathVariable("car-part-name-description-filter") String carPartNameDescriptionFilter) {
        return new ResponseEntity<>(
                carPartService.getAllGroupedByBrandAndModelWithFilter(carPartNameDescriptionFilter),
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

    @PatchMapping("/{id}")
    public ResponseEntity<Integer> modifyCarPart(@PathVariable("id") long id,
                                                 @RequestBody CarPartDto carPartDto) {
        return new ResponseEntity<>(
                carPartService.modifyCarPart(id, carPartDto),
                HttpStatus.OK
        );
    }

    @PostMapping("/{id}/service-actions")
    public ResponseEntity<Void> addServiceAction(@PathVariable("id") long id,
                                                 @RequestBody ServiceActionDto serviceActionDto) {
        carPartService.addServiceAction(id, serviceActionDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/sales-arguments")
    public ResponseEntity<Integer> deleteSalesArguments(@PathVariable("id") long id) {
        return new ResponseEntity<>(
                carPartService.deleteSalesArguments(id),
                HttpStatus.OK
        );
    }

}
