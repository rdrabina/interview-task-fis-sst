package com.task.interview.sst.fis.commands.init;

import com.task.interview.sst.fis.entities.Year;
import com.task.interview.sst.fis.repositories.YearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.task.interview.sst.fis.constants.ModelConstants.MODEL_PRODUCTION_END_DATE;
import static com.task.interview.sst.fis.constants.ModelConstants.MODEL_PRODUCTION_START_DATE;

@Component
public class YearInit implements EntityInit {

    private final YearRepository yearRepository;

    @Autowired
    public YearInit(YearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }

    @Override
    @Transactional
    public void execute() {
        List<Year> years = IntStream.range(MODEL_PRODUCTION_START_DATE, MODEL_PRODUCTION_END_DATE)
                .mapToObj(this::createYear)
                .collect(Collectors.toList());

        save(years);
    }

    private void save(List<Year> years) {
        yearRepository.saveAll(years);
    }

    private Year createYear(int value) {
        Year year = new Year();
        year.setValue(value);
        year.setProductionStartDateModels(new ArrayList<>());
        year.setProductionEndDateModels(new ArrayList<>());

        return year;
    }

}
