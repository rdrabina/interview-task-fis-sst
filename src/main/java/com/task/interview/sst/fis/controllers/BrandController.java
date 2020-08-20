package com.task.interview.sst.fis.controllers;

import com.task.interview.sst.fis.entities.Brand;
import com.task.interview.sst.fis.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<Brand> getBrand(@PathVariable Long id) {
        return new ResponseEntity<>(
                brandService.getBrandById(id),
                HttpStatus.OK
        );
    }

}
