package com.task.interview.sst.fis.dtos;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CarPartDto {

    private String name;
    private String description;
    private BigDecimal price;
    private boolean onStock;
    private int shipmentWithinDays;

}
