package com.task.interview.sst.fis.entities;

import com.task.interview.sst.fis.exceptions.StartDateAfterEndDateException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "production_start_date_id")
    private Year productionStartDate;

    @ManyToOne
    @JoinColumn(name = "production_end_date_id")
    private Year productionEndDate;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToMany(mappedBy = "models")
    private Set<CarPart> carParts;

    public void setProductionStartDate(Year productionStartDate) {
        if (Objects.nonNull(productionEndDate) && productionEndDate.getValue() < productionStartDate.getValue()) {
            throw new StartDateAfterEndDateException();
        }

        this.productionStartDate = productionStartDate;
    }

    public void setProductionEndDate(Year productionEndDate) {
        if (Objects.nonNull(productionStartDate) && productionEndDate.getValue() < productionStartDate.getValue()) {
            throw new StartDateAfterEndDateException();
        }

        this.productionEndDate = productionEndDate;
    }

    public void setBrand(Model model, Brand brand) {
        model.setBrand(brand);
        brand.getModels().add(model);
    }

    public void setProductionDate(Model model, Year year, Consumer<Year> consumer, Supplier<List<Model>> supplier) {
        consumer.accept(year);
        supplier.get().add(model);
    }

}
