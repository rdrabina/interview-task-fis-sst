package com.task.interview.sst.fis.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "car_part")
@NoArgsConstructor
@Getter
@Setter
public class CarPart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    private Date productionStartDate;

    private Date productionEndDate;

    @Embedded
    private CarPartDetails carPartDetails;

    @OneToMany(
            mappedBy = "car_part",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SalesArgument> salesArguments;

    @OneToMany(
            mappedBy = "car_part",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ServiceAction> serviceActions;

}
