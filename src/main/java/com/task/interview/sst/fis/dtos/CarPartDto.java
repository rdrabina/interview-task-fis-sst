package com.task.interview.sst.fis.dtos;

import com.task.interview.sst.fis.entities.CarPart;
import com.task.interview.sst.fis.entities.CarPartDetails;
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

    public static CarPartDto createCarPartDto(CarPart carPart) {
        CarPartDetails carPartDetails = carPart.getCarPartDetails();

        return CarPartDto.builder()
                .name(carPartDetails.getName())
                .description(carPartDetails.getDescription())
                .price(carPartDetails.getPrice())
                .onStock(carPartDetails.isOnStock())
                .shipmentWithinDays(carPartDetails.getShipmentWithinDays())
                .build();
    }

}
