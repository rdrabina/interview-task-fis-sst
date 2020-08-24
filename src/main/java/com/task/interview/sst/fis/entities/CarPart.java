package com.task.interview.sst.fis.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "car_part")
@NoArgsConstructor
@Getter
@Setter
public class CarPart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "car_part_model",
            joinColumns = @JoinColumn(name = "car_part_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private Set<Model> models;

    @Embedded
    private CarPartDetails carPartDetails;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "car_part_sales_argument",
            joinColumns = @JoinColumn(name = "car_part_id"),
            inverseJoinColumns = @JoinColumn(name = "sales_argument_id")
    )
    private Set<SalesArgument> salesArguments;

    @OneToMany(
            mappedBy = "carPart",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ServiceAction> serviceActions;

    public void setModels(CarPart carPart, Set<Model> models) {
        carPart.setModels(models);
        models.forEach(model -> model.getCarParts().add(carPart));
    }

    public void setSalesArguments(CarPart carPart, Set<SalesArgument> salesArguments) {
        carPart.setSalesArguments(salesArguments);
        salesArguments.forEach(salesArgument -> salesArgument.getCarParts().add(carPart));
    }

    public String getCarPartName() {
        return this.getCarPartDetails().getName();
    }

}
