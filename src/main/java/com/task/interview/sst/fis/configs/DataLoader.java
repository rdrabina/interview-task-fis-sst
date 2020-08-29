package com.task.interview.sst.fis.configs;

import com.task.interview.sst.fis.commands.init.EntityInitCommand;
import com.task.interview.sst.fis.commands.init.factory.EntityInitFactory;
import com.task.interview.sst.fis.entities.Brand;
import com.task.interview.sst.fis.enums.CarBrand;
import com.task.interview.sst.fis.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.task.interview.sst.fis.commands.init.enums.EntityInitStep.*;


@Component
public class DataLoader implements ApplicationRunner {

    private Map<Integer, EntityInitCommand> entityInitMap;

    private final EntityInitFactory entityInitFactory;
    private final BrandRepository brandRepository;

    @Autowired
    public DataLoader(EntityInitFactory entityInitFactory,
                      BrandRepository brandRepository) {
        this.entityInitFactory = entityInitFactory;
        this.brandRepository = brandRepository;
        initEntityInitMap();
    }

    private void initEntityInitMap() {
        entityInitMap = new TreeMap<>();
        entityInitMap.put(1, entityInitFactory.resolve(BRAND_INIT));
        entityInitMap.put(2, entityInitFactory.resolve(YEAR_INIT));
        entityInitMap.put(3, entityInitFactory.resolve(MODEL_INIT));
        entityInitMap.put(4, entityInitFactory.resolve(SERVICE_ACTION_NAME_INIT));
        entityInitMap.put(5, entityInitFactory.resolve(SALES_ARGUMENT_INIT));
        entityInitMap.put(6, entityInitFactory.resolve(CAR_PART_INIT));
        entityInitMap.put(7, entityInitFactory.resolve(SERVICE_ACTION_INIT));
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        List<Brand> brands = brandRepository.findAllByNameIgnoreCase(CarBrand.AUDI.name());

        if (CollectionUtils.isEmpty(brands)) {
            entityInitMap.values()
                    .forEach(EntityInitCommand::execute);
        }
    }

}
