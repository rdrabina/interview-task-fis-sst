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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.task.interview.sst.fis.utils.GenericUtils.generateRandomSubsetFromList;
import static com.task.interview.sst.fis.utils.GenericUtils.getRandomObjectFromList;

@Component
public class CarPartInit implements EntityInitCommand {

    private static final int CAR_PARTS_AMOUNT = 20;
    private static final int MODELS_LIMIT = 4;
    private static final int SALES_ARGUMENTS_LIMIT = 4;
    private static final int SHIPMENT_DAYS_LIMIT = 8;
    private static final double PRICE_LOWER_LIMIT = 100.00d;
    private static final double PRICE_UPPER_LIMIT = 1500.00d;
    private static final int DECIMAL_PLACES = 2;
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
    @Transactional
    public void execute() {
        List<Model> models = modelRepository.findAll();
        List<SalesArgument> salesArguments = salesArgumentRepository.findAll();

        ThreadLocalRandom current = ThreadLocalRandom.current();
        List<CarPart> carParts = IntStream.range(0, CAR_PARTS_AMOUNT)
                .mapToObj(i -> createCarPart(current, models, salesArguments))
                .collect(Collectors.toList());

        save(models, salesArguments, carParts);
    }

    private void save(List<Model> models, List<SalesArgument> salesArguments, List<CarPart> carParts) {
        modelRepository.saveAll(models);
        salesArgumentRepository.saveAll(salesArguments);
        carPartRepository.saveAll(carParts);
    }

    private CarPart createCarPart(ThreadLocalRandom current, List<Model> models, List<SalesArgument> salesArguments) {
        CarPart carPart = new CarPart();
        Set<Model> carPartModels = generateRandomSubsetFromList(current, models, MODELS_LIMIT);
        carPart.setModels(carPart, carPartModels);
        carPart.setServiceActions(new ArrayList<>());
        Set<SalesArgument> carPartSalesArguments = generateRandomSubsetFromList(current, salesArguments, SALES_ARGUMENTS_LIMIT);
        carPart.setSalesArguments(carPart, carPartSalesArguments);
        carPart.setCarPartDetails(createCarPartDetails(current));

        return carPart;
    }

    private CarPartDetails createCarPartDetails(ThreadLocalRandom current) {
        CarPartDetails carPartDetails = new CarPartDetails();
        carPartDetails.setName(getRandomObjectFromList(CAR_PART_NAMES));
        carPartDetails.setDescription(getRandomObjectFromList(CAR_PART_DESCRIPTIONS));
        BigDecimal price = BigDecimal.valueOf(current.nextDouble(PRICE_LOWER_LIMIT, PRICE_UPPER_LIMIT))
                .setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
        carPartDetails.setPrice(price);
        carPartDetails.setShipmentWithinDays(current.nextInt(1, SHIPMENT_DAYS_LIMIT));
        carPartDetails.setOnStock(current.nextBoolean());

        return carPartDetails;
    }

}
