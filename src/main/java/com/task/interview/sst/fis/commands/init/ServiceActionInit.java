package com.task.interview.sst.fis.commands.init;

import com.task.interview.sst.fis.entities.CarPart;
import com.task.interview.sst.fis.entities.ServiceAction;
import com.task.interview.sst.fis.entities.ServiceActionName;
import com.task.interview.sst.fis.repositories.CarPartRepository;
import com.task.interview.sst.fis.repositories.ServiceActionNameRepository;
import com.task.interview.sst.fis.repositories.ServiceActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.task.interview.sst.fis.utils.DateUtils.generateDateBetween;
import static com.task.interview.sst.fis.utils.GenericUtils.getRandomObjectFromList;

@Component
public class ServiceActionInit implements EntityInit {

    private final static Date START_DATE;
    private final static Date PIVOT_DATE;
    private final static Date END_DATE;

    static {
        // 01-01-2019
        START_DATE = new Date(1546300800000L);
        // 01-01-2020
        PIVOT_DATE = new Date(1577836800000L);
        // 01-01-2021
        END_DATE = new Date(1609459200000L);
    }

    private final ServiceActionRepository serviceActionRepository;
    private final ServiceActionNameRepository serviceActionNameRepository;
    private final CarPartRepository carPartRepository;

    @Autowired
    public ServiceActionInit(ServiceActionRepository serviceActionRepository,
                             ServiceActionNameRepository serviceActionNameRepository,
                             CarPartRepository carPartRepository) {
        this.serviceActionRepository = serviceActionRepository;
        this.serviceActionNameRepository = serviceActionNameRepository;
        this.carPartRepository = carPartRepository;
    }

    @Override
    public void execute() {
        List<ServiceActionName> serviceActionNames = serviceActionNameRepository.findAll();
        List<CarPart> carParts = carPartRepository.findAll();

        List<ServiceAction> serviceActions = IntStream.range(0, 50)
                .mapToObj(i -> {
                    ServiceAction serviceAction = new ServiceAction();
                    serviceAction.setCarPart(serviceAction, getRandomObjectFromList(carParts));
                    serviceAction.setServiceActionName(serviceAction, getRandomObjectFromList(serviceActionNames));
                    serviceAction.setStartDate(generateDateBetween(START_DATE, PIVOT_DATE));
                    serviceAction.setEndDate(generateDateBetween(PIVOT_DATE, END_DATE));

                    return serviceAction;
                })
                .collect(Collectors.toList());

        save(serviceActionNames, carParts, serviceActions);
    }

    private void save(List<ServiceActionName> serviceActionNames, List<CarPart> carParts, List<ServiceAction> serviceActions) {
        serviceActionRepository.saveAll(serviceActions);
        serviceActionNameRepository.saveAll(serviceActionNames);
        carPartRepository.saveAll(carParts);
    }

}
