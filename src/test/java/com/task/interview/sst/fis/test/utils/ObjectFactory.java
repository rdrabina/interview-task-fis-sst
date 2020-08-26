package com.task.interview.sst.fis.test.utils;

import com.task.interview.sst.fis.dtos.CarPartAvailabilityDto;
import com.task.interview.sst.fis.dtos.CarPartDto;
import com.task.interview.sst.fis.dtos.ServiceActionDto;
import com.task.interview.sst.fis.entities.*;

import java.math.BigDecimal;
import java.util.*;

import static com.task.interview.sst.fis.utils.DateUtils.addDaysToDate;

public class ObjectFactory {

    public static List<Brand> createBrandList() {
        CarPart carPart = createCarPartList().get(0);
        Set<Model> models = carPart.getModels();
        Model model = (Model) models.toArray()[0];

        return Collections.singletonList(model.getBrand());
    }

    public static List<CarPart> createCarPartList() {
        SalesArgument salesArgument = new SalesArgument();
        salesArgument.setName("sales argument test");
        salesArgument.setCarParts(new HashSet<>());

        CarPartDetails carPartDetails = new CarPartDetails();
        carPartDetails.setName("car part name test");
        carPartDetails.setDescription("car part description test");
        carPartDetails.setOnStock(true);
        carPartDetails.setPrice(BigDecimal.valueOf(100.00));
        carPartDetails.setShipmentWithinDays(3);

        Brand brand = new Brand();
        brand.setName("brand name test");
        brand.setModels(new ArrayList<>());

        Model model = new Model();
        model.setName("model name test");
        model.setBrand(model, brand);
        model.setCarParts(new HashSet<>());

        CarPart carPart = new CarPart();
        carPart.setSalesArguments(carPart, new HashSet<>(Collections.singletonList(salesArgument)));
        carPart.setCarPartDetails(carPartDetails);
        carPart.setModels(carPart, new HashSet<>(Collections.singletonList(model)));
        carPart.setServiceActions(new ArrayList<>());

        return Collections.singletonList(carPart);
    }

    public static Map<String, Map<String, Set<CarPartDto>>> createGroupedByBrandAndModelMap() {
        Map<String, Set<CarPartDto>> modelCarPartDtoMap = new HashMap<>();
        modelCarPartDtoMap.put("model name test", new HashSet<>(Collections.singletonList(createCarPartDto())));

        Map<String, Map<String, Set<CarPartDto>>> brandModelCarPartDtoMap = new HashMap<>();
        brandModelCarPartDtoMap.put("brand name test", modelCarPartDtoMap);

        return brandModelCarPartDtoMap;
    }

    public static CarPartDto createCarPartDto() {
        CarPartDto carPartDto = new CarPartDto();
        carPartDto.setName("car part name test");
        carPartDto.setDescription("car part description test");
        carPartDto.setOnStock(true);
        carPartDto.setPrice(BigDecimal.valueOf(100.00));
        carPartDto.setShipmentWithinDays(3);

        return carPartDto;
    }

    public static CarPartAvailabilityDto createCarPartAvailabilityDto() {
        return new CarPartAvailabilityDto(true, addDaysToDate(3));
    }

    public static ServiceActionDto createServiceActionDto() {
        ServiceActionDto serviceActionDto = new ServiceActionDto();
        serviceActionDto.setCarPartName("car part name test");
        serviceActionDto.setName("service action name test");

        return serviceActionDto;
    }

}
