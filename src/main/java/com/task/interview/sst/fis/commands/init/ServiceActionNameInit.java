package com.task.interview.sst.fis.commands.init;

import com.task.interview.sst.fis.entities.ServiceActionName;
import com.task.interview.sst.fis.repositories.ServiceActionNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServiceActionNameInit implements EntityInit {

    private static List<String> SERVICE_ACTION_NAMES;

    private final ServiceActionNameRepository serviceActionNameRepository;

    @Autowired
    public ServiceActionNameInit(ServiceActionNameRepository serviceActionNameRepository) {
        this.serviceActionNameRepository = serviceActionNameRepository;
    }

    static {
        SERVICE_ACTION_NAMES = Arrays.asList(
                "Clutch replacement",
                "Engine repair",
                "Patching the tire",
                "Change of a light bulb",
                "Darkening the windows"
        );
    }

    @Override
    public void execute() {
        List<ServiceActionName> serviceActionNames = SERVICE_ACTION_NAMES.stream()
                .map(this::createServiceActionName)
                .collect(Collectors.toList());

        save(serviceActionNames);
    }

    private void save(List<ServiceActionName> serviceActionNames) {
        serviceActionNameRepository.saveAll(serviceActionNames);
    }

    private ServiceActionName createServiceActionName(String name) {
        ServiceActionName serviceActionName = new ServiceActionName();
        serviceActionName.setValue(name);
        serviceActionName.setServiceActions(new ArrayList<>());

        return serviceActionName;
    }

}
