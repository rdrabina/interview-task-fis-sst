package com.task.interview.sst.fis.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class CarPartDetails {

    private String name;

    private String description;

    private BigDecimal price;

    @Column(name = "on_stock")
    private boolean onStock;

    @Column(name = "shipment_within_days")
    private int shipmentWithinDays;

}
