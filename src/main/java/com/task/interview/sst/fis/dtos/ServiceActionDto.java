package com.task.interview.sst.fis.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.task.interview.sst.fis.entities.ServiceAction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ServiceActionDto {

    String name;

    String carPartName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date endDate;

    public static ServiceActionDto createServiceActionDto(ServiceAction serviceAction) {
        ServiceActionDto serviceActionDto = new ServiceActionDto();
        serviceActionDto.setName(serviceAction.getServiceActionNameValue());
        serviceActionDto.setCarPartName(serviceAction.getCarPartName());
        serviceActionDto.setStartDate(serviceAction.getStartDate());
        serviceActionDto.setEndDate(serviceAction.getEndDate());

        return serviceActionDto;
    }

}
