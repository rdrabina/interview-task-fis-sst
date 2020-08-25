package com.task.interview.sst.fis.controllers;

import com.task.interview.sst.fis.dtos.CarPartDto;
import com.task.interview.sst.fis.services.BrandService;
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
@RequestMapping("/api/v1/brands")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Map<String, Map<String, Set<CarPartDto>>>> getBrand(@PathVariable("name") String name) {
        return new ResponseEntity<>(
                brandService.getBrandByNameWithModelsAndCarParts(name),
                HttpStatus.OK
        );
    }

}
