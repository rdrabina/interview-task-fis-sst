package com.task.interview.sst.fis.commands.init;

import com.task.interview.sst.fis.entities.CarPart;
import com.task.interview.sst.fis.entities.CarPartDetails;
import com.task.interview.sst.fis.entities.Model;
import com.task.interview.sst.fis.entities.SalesArgument;
import com.task.interview.sst.fis.repositories.CarPartRepository;
import com.task.interview.sst.fis.repositories.ModelRepository;
import com.task.interview.sst.fis.repositories.SalesArgumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.task.interview.sst.fis.utils.GenericUtils.getRandomObjectFromList;

@Component
public class CarPartInit implements EntityInit {

    private static final List<String> CAR_PART_NAMES;
    private static final List<String> CAR_PART_DESCRIPTIONS;

    static {
        CAR_PART_NAMES = Arrays.asList(
                "Clutch",
                "Brake pads",
                "Shock absorbers",
                "Fuel filter",
                "Bearing"
        );

        CAR_PART_DESCRIPTIONS = Arrays.asList(
                "This is an auto part description",
                "This part is for fast cars",
                "Cars want that part",
                "Your car will spin like out of this world",
                "Your children will thank you for buying this part while driving and still using it"
        );
    }

    private final CarPartRepository carPartRepository;
    private final ModelRepository modelRepository;
    private final SalesArgumentRepository salesArgumentRepository;

    @Autowired
    public CarPartInit(CarPartRepository carPartRepository,
                       ModelRepository modelRepository,
                       SalesArgumentRepository salesArgumentRepository) {
        this.carPartRepository = carPartRepository;
        this.modelRepository = modelRepository;
        this.salesArgumentRepository = salesArgumentRepository;
    }

    @Override
    public void execute() {
        List<Model> models = modelRepository.findAll();
        List<SalesArgument> salesArguments = salesArgumentRepository.findAll();

        ThreadLocalRandom current = ThreadLocalRandom.current();
        List<CarPart> carParts = IntStream.range(0, 20)
                .mapToObj(i -> {
                    int numberOfModels = current.nextInt(3) + 1;
                    Set<Model> carPartModels = IntStream.range(0, numberOfModels)
                            .mapToObj(j -> getRandomObjectFromList(models))
                            .collect(Collectors.toSet());

                    int numberOfSalesArguments = current.nextInt(3) + 1;
                    Set<SalesArgument> carPartSalesArguments = IntStream.range(0, numberOfSalesArguments)
                            .mapToObj(j -> getRandomObjectFromList(salesArguments))
                            .collect(Collectors.toSet());

                    CarPart carPart = new CarPart();
                    carPart.setModels(carPart, carPartModels);
                    carPart.setServiceActions(new ArrayList<>());
                    carPart.setSalesArguments(carPart, carPartSalesArguments);

                    CarPartDetails carPartDetails = new CarPartDetails();
                    carPartDetails.setName(getRandomObjectFromList(CAR_PART_NAMES));
                    carPartDetails.setDescription(getRandomObjectFromList(CAR_PART_DESCRIPTIONS));
                    BigDecimal price = BigDecimal.valueOf(current.nextDouble(100.00, 1500.00)).setScale(2, RoundingMode.HALF_UP);
                    carPartDetails.setPrice(price);
                    carPartDetails.setShipmentWithinDays(current.nextInt(1, 8));
                    carPartDetails.setOnStock(current.nextBoolean());
                    carPart.setCarPartDetails(carPartDetails);

                    return carPart;
                })
                .collect(Collectors.toList());

        save(models, salesArguments, carParts);
    }

    private void save(List<Model> models, List<SalesArgument> salesArguments, List<CarPart> carParts) {
        modelRepository.saveAll(models);
        salesArgumentRepository.saveAll(salesArguments);
        carPartRepository.saveAll(carParts);
    }

}
