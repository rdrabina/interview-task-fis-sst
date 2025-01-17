package com.task.interview.sst.fis.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CarPartAvailabilityDto {

    private boolean onStock;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date possibleShipmentDate;

}
