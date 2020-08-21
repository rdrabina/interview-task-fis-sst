package com.task.interview.sst.fis.configs;

import com.task.interview.sst.fis.entities.Brand;
import com.task.interview.sst.fis.entities.CarPart;
import com.task.interview.sst.fis.entities.Model;
import com.task.interview.sst.fis.enums.*;
import com.task.interview.sst.fis.repositories.CarPartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.task.interview.sst.fis.enums.CarBrand.*;

@Component
public class DataLoader implements ApplicationRunner {

    private static final Map<CarBrand, List<CarModel>> BRAND_MODEL_RELATION_MAP;

    static {
        BRAND_MODEL_RELATION_MAP = new HashMap<>();
        BRAND_MODEL_RELATION_MAP.put(AUDI, Arrays.asList(AudiCarModel.values()));
        BRAND_MODEL_RELATION_MAP.put(VOLKSWAGEN, Arrays.asList(VolkswagenCarModel.values()));
        BRAND_MODEL_RELATION_MAP.put(BMW, Arrays.asList(BmwCarModel.values()));
    }

    private final CarPartRepository carPartRepository;

    @Autowired
    public DataLoader(CarPartRepository carPartRepository) {
        this.carPartRepository = carPartRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Brand brand = new Brand();
        brand.setName(AUDI.name());

        Model model = new Model();
        model.setBrand(brand);
        model.setName(AudiCarModel.A1.name());

        CarPart carPart = new CarPart();
        carPart.setModel(model);
        carPart.setProductionStartDate();
        carPart.setProductionEndDate();
        carPart.setCarPartDetails();

        carPartRepository.save(carPart)
    }

}
