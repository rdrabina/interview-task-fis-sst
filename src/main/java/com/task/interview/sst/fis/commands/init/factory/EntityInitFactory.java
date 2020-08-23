package com.task.interview.sst.fis.commands.init.factory;

import com.task.interview.sst.fis.commands.init.EntityInit;
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

    private Map<EntityInitStep, EntityInit> entityInits;

    private final EntityInit brandInit;
    private final EntityInit modelInit;
    private final EntityInit yearInit;
    private final EntityInit serviceActionNameInit;
    private final EntityInit serviceActionInit;
    private final EntityInit salesArgumentInit;
    private final EntityInit carPartInit;

    @Autowired
    public EntityInitFactory(@Qualifier("brandInit") EntityInit brandInit,
                             @Qualifier("modelInit") EntityInit modelInit,
                             @Qualifier("yearInit") EntityInit yearInit,
                             @Qualifier("serviceActionNameInit") EntityInit serviceActionNameInit,
                             @Qualifier("serviceActionInit") EntityInit serviceActionInit,
                             @Qualifier("salesArgumentInit") EntityInit salesArgumentInit,
                             @Qualifier("carPartInit") EntityInit carPartInit) {
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

    public EntityInit resolve(EntityInitStep entityInitStep) {
        return Optional.ofNullable(entityInits.get(entityInitStep)).orElseThrow(EntityInitStepNotFoundException::new);
    }

}
