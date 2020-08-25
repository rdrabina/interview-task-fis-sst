package com.task.interview.sst.fis.commands.init.factory;

import com.task.interview.sst.fis.commands.init.EntityInitCommand;
import com.task.interview.sst.fis.commands.init.enums.EntityInitStep;
import com.task.interview.sst.fis.exceptions.EntityInitStepNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.task.interview.sst.fis.commands.init.enums.EntityInitStep.*;

@Component
public class EntityInitFactory {

    private Map<EntityInitStep, EntityInitCommand> entityInits;

    private final EntityInitCommand brandInit;
    private final EntityInitCommand modelInit;
    private final EntityInitCommand yearInit;
    private final EntityInitCommand serviceActionNameInit;
    private final EntityInitCommand serviceActionInit;
    private final EntityInitCommand salesArgumentInit;
    private final EntityInitCommand carPartInit;

    @Autowired
    public EntityInitFactory(@Qualifier("brandInit") EntityInitCommand brandInit,
                             @Qualifier("modelInit") EntityInitCommand modelInit,
                             @Qualifier("yearInit") EntityInitCommand yearInit,
                             @Qualifier("serviceActionNameInit") EntityInitCommand serviceActionNameInit,
                             @Qualifier("serviceActionInit") EntityInitCommand serviceActionInit,
                             @Qualifier("salesArgumentInit") EntityInitCommand salesArgumentInit,
                             @Qualifier("carPartInit") EntityInitCommand carPartInit) {
        this.brandInit = brandInit;
        this.modelInit = modelInit;
        this.yearInit = yearInit;
        this.serviceActionNameInit = serviceActionNameInit;
        this.serviceActionInit = serviceActionInit;
        this.salesArgumentInit = salesArgumentInit;
        this.carPartInit = carPartInit;
        initMap();
    }

    private void initMap() {
        entityInits = new HashMap<>();
        entityInits.put(BRAND_INIT, brandInit);
        entityInits.put(MODEL_INIT, modelInit);
        entityInits.put(YEAR_INIT, yearInit);
        entityInits.put(SERVICE_ACTION_NAME_INIT, serviceActionNameInit);
        entityInits.put(SERVICE_ACTION_INIT, serviceActionInit);
        entityInits.put(SALES_ARGUMENT_INIT, salesArgumentInit);
        entityInits.put(CAR_PART_INIT, carPartInit);
    }

    public EntityInitCommand resolve(EntityInitStep entityInitStep) {
        return Optional.ofNullable(entityInits.get(entityInitStep))
                .orElseThrow(EntityInitStepNotFoundException::new);
    }

}
