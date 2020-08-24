package com.task.interview.sst.fis.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BrandModelCarPartDto {

    private String brand;
    private String model;
    private CarPartDto carPartDto;
}
