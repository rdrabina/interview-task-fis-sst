package com.task.interview.sst.fis.enums.factories;

import com.task.interview.sst.fis.enums.AudiCarModel;
import com.task.interview.sst.fis.enums.BmwCarModel;
import com.task.interview.sst.fis.enums.CarModel;
import com.task.interview.sst.fis.enums.VolkswagenCarModel;
import com.task.interview.sst.fis.exceptions.CarModelNotFoundException;

import java.util.*;

import static com.task.interview.sst.fis.utils.GenericUtils.fromArrayToUnmodifiableList;

public class CarModelFactory {

    private static final Map<String, List<CarModel>> CAR_MODELS;

    static {
        CAR_MODELS = new HashMap<>();
        CAR_MODELS.put("AUDI", fromArrayToUnmodifiableList(AudiCarModel.values()));
        CAR_MODELS.put("BMW", fromArrayToUnmodifiableList(BmwCarModel.values()));
        CAR_MODELS.put("VOLKSWAGEN", fromArrayToUnmodifiableList(VolkswagenCarModel.values()));
    }

    public static List<CarModel> resolve(String name) {
        return Optional.ofNullable(CAR_MODELS.get(name)).orElseThrow(CarModelNotFoundException::new);
    }

}
