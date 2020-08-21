package com.task.interview.sst.fis.entities;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class CarPartDetails {

    private String name;

    private String description;

    private BigDecimal price;

    private boolean onStock;

    private int shipmentWithinDays;

}
