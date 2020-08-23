package com.task.interview.sst.fis.commands.init;

import com.task.interview.sst.fis.entities.Brand;
import com.task.interview.sst.fis.enums.CarBrand;
import com.task.interview.sst.fis.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BrandInit implements EntityInit {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandInit(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public void execute() {
        List<Brand> brands = Stream.of(CarBrand.values())
                .map(this::createBrand)
                .collect(Collectors.toList());

        save(brands);
    }

    private void save(List<Brand> brands) {
        brandRepository.saveAll(brands);
    }

    private Brand createBrand(CarBrand carBrand) {
        Brand brand = new Brand();
        brand.setName(carBrand.name());
        brand.setModelDetails(new ArrayList<>());

        return brand;
    }

}
