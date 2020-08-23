package com.task.interview.sst.fis.commands.init;

import com.task.interview.sst.fis.entities.SalesArgument;
import com.task.interview.sst.fis.repositories.SalesArgumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SalesArgumentInit implements EntityInit {

    private static final List<String> SALES_ARGUMENT_NAMES;

    static {
        SALES_ARGUMENT_NAMES = Arrays.asList(
                "Prize",
                "Availability",
                "Reliability",
                "Durability",
                "Ease of use"
        );
    }

    private final SalesArgumentRepository salesArgumentRepository;

    @Autowired
    public SalesArgumentInit(SalesArgumentRepository salesArgumentRepository) {
        this.salesArgumentRepository = salesArgumentRepository;
    }

    @Override
    @Transactional
    public void execute() {
        List<SalesArgument> salesArguments = SALES_ARGUMENT_NAMES.stream()
                .map(this::createSalesArgument)
                .collect(Collectors.toList());

        save(salesArguments);
    }

    private void save(List<SalesArgument> salesArguments) {
        salesArgumentRepository.saveAll(salesArguments);
    }

    private SalesArgument createSalesArgument(String name) {
        SalesArgument salesArgument = new SalesArgument();
        salesArgument.setName(name);
        salesArgument.setCarParts(new HashSet<>());

        return salesArgument;
    }

}
