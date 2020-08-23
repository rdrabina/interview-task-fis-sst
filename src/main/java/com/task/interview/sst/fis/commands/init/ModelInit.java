package com.task.interview.sst.fis.commands.init;

import com.task.interview.sst.fis.entities.Brand;
import com.task.interview.sst.fis.entities.Model;
import com.task.interview.sst.fis.entities.Year;
import com.task.interview.sst.fis.enums.CarModel;
import com.task.interview.sst.fis.enums.factories.CarModelFactory;
import com.task.interview.sst.fis.repositories.BrandRepository;
import com.task.interview.sst.fis.repositories.ModelRepository;
import com.task.interview.sst.fis.repositories.YearRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.task.interview.sst.fis.constants.ModelConstants.MODEL_PRODUCTION_END_DATE;
import static com.task.interview.sst.fis.constants.ModelConstants.MODEL_PRODUCTION_START_DATE;

@Component
public class ModelInit implements EntityInit {

    private final ModelRepository modelRepository;
    private final BrandRepository brandRepository;
    private final YearRepository yearRepository;

    @Autowired
    public ModelInit(ModelRepository modelRepository,
                     BrandRepository brandRepository,
                     YearRepository yearRepository) {
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
        this.yearRepository = yearRepository;
    }

    @Override
    @Transactional
    public void execute() {
        List<Brand> brands = brandRepository.findAll();
        List<Year> years = yearRepository.findAll();

        List<Model> models = brands.stream()
                .map(brand -> createModels(brand, years))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        save(models, brands, years);
    }

    private void save(List<Model> models, List<Brand> brands, List<Year> years) {
        modelRepository.saveAll(models);
        brandRepository.saveAll(brands);
        yearRepository.saveAll(years);
    }

    private List<Model> createModels(Brand brand, List<Year> years) {
        List<CarModel> carModels = CarModelFactory.resolve(brand.getName());

        return carModels.stream()
                .map(carModel -> createModel(brand, carModel, years))
                .collect(Collectors.toList());
    }

    private Model createModel(Brand brand, CarModel carModel, List<Year> years) {
        Model model = new Model();
        model.setBrand(model, brand);
        model.setName(carModel.name());
        ProductionDate productionDate = resolveProductionDate(years);
        Year start = productionDate.getStart();
        Year end = productionDate.getEnd();
        model.setProductionDate(model, start, model::setProductionStartDate, start::getProductionStartDateModels);
        model.setProductionDate(model, end, model::setProductionEndDate, end::getProductionEndDateModels);
        model.setCarParts(new HashSet<>());

        return model;
    }

    @AllArgsConstructor
    @Getter
    private static class ProductionDate {
        private Year start;
        private Year end;
    }

    private ProductionDate resolveProductionDate(List<Year> years) {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        int start = current.nextInt(MODEL_PRODUCTION_START_DATE, MODEL_PRODUCTION_END_DATE);
        int end = current.nextInt(start, MODEL_PRODUCTION_END_DATE);

        return new ProductionDate(
                findYear(years, start, 0),
                findYear(years, end, years.size() - 1)
        );
    }

    private Year findYear(List<Year> years, int year, int defaultIndex) {
        return years.stream()
                .filter(y -> y.getValue() == year)
                .findAny()
                .orElseGet(() -> years.get(defaultIndex));
    }

}
