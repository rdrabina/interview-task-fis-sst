package com.task.interview.sst.fis.dtos;

import com.task.interview.sst.fis.entities.CarPart;
import com.task.interview.sst.fis.entities.CarPartDetails;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class CarPartDto {

    private String name;
    private String description;
    private BigDecimal price;
    private boolean onStock;
    private int shipmentWithinDays;

    public static CarPartDto createCarPartDto(CarPart carPart) {
        CarPartDetails carPartDetails = carPart.getCarPartDetails();

        CarPartDto carPartDto = new CarPartDto();
        carPartDto.setName(carPartDetails.getName());
        carPartDto.setDescription(carPartDetails.getDescription());
        carPartDto.setPrice(carPartDetails.getPrice());
        carPartDto.setOnStock(carPartDetails.isOnStock());
        carPartDto.setShipmentWithinDays(carPartDetails.getShipmentWithinDays());

        return carPartDto;
    }

}
