package com.task.interview.sst.fis.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class BrandModelCarPartDto {

    private String brand;
    private String model;
    private CarPartDto carPartDto;
    private Set<CarPartDto> carPartDtos;

}
